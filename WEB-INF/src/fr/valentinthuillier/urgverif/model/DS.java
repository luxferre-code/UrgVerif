package fr.valentinthuillier.urgverif.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import fr.valentinthuillier.urgverif.Log;

public class DS {

    public static final String path;
    static {
        try {
            path = new File(DS.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getParent();
        } catch (URISyntaxException e) {
            throw new ExceptionInInitializerError(e);
        }
        System.out.println(path);
    }

    private static final String CONFIG_FILE_PATH = "/config-urgverif.prop";
    private static final String VERSION = "1.0";
    private static volatile DS instance = null;
    private final String nom;
    private final String mdp;
    private final String url;

    private DS() throws Exception {
        Log.info("Loading database configuration");
        Properties properties = loadProperties();

        this.url = properties.getProperty("url");
        this.nom = properties.getProperty("login");
        this.mdp = properties.getProperty("password");

        validateVersion(properties);
    }

    private Properties loadProperties() throws IOException, ClassNotFoundException {
        Properties properties = new Properties();
        Path configFilePath = Paths.get(CONFIG_FILE_PATH);

        if (!Files.exists(configFilePath)) {
            initConfigFile(configFilePath);
        }

        try (FileInputStream fis = new FileInputStream(configFilePath.toFile())) {
            properties.load(fis);
        }

        Class.forName(properties.getProperty("driver"));
        return properties;
    }

    private void validateVersion(Properties properties) throws Exception {
        if (!VERSION.equals(properties.getProperty("version"))) {
            Log.error("Version mismatch, please update your configuration file and restart the server.");
            Files.deleteIfExists(Paths.get(CONFIG_FILE_PATH));
            initConfigFile(Paths.get(CONFIG_FILE_PATH));
        }
    }

    public static DS getInstance() throws Exception {
        if (instance == null) {
            synchronized (DS.class) {
                if (instance == null) {
                    instance = new DS();
                }
            }
        }
        return instance;
    }

    public static boolean isConfigured() {
        try {
            getInstance();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void initConfigFile(Path configFilePath) throws IOException {
        Files.createDirectories(configFilePath.getParent());
        Properties properties = new Properties();
        properties.setProperty("driver", "org.postgresql.Driver");
        properties.setProperty("url", "jdbc:postgresql://valentin-thuillier.fr:5432/urgverif");
        properties.setProperty("login", "urgverif");
        properties.setProperty("password", "TOSET");
        properties.setProperty("version", VERSION);

        try (FileOutputStream fos = new FileOutputStream(configFilePath.toFile())) {
            properties.store(fos, "UrgVerif configuration file - Just set the password");
        }

        Log.info("Config file created, please modify and restart the server.");
    }

    public static void configure(String driver, String url, String login, String password) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("driver", driver);
        properties.setProperty("url", url);
        properties.setProperty("login", login);
        properties.setProperty("password", password);
        properties.setProperty("version", VERSION);

        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE_PATH)) {
            properties.store(fos, "UrgVerif configuration file");
        }

        Log.info("Configuration file updated, please restart the server.");
    }

    public static Connection getConnection() {
        try {
            return getInstance().createConnection();
        } catch (Exception e) {
            return null;
        }
    }

    private Connection createConnection() throws Exception {
        return DriverManager.getConnection(this.url, this.nom, this.mdp);
    }

    public static void startInstallSQLFiles() {
        List<String> files = new ArrayList<>();
        try {
            Files.walk(Paths.get(path))
                 .filter(Files::isRegularFile)
                 .filter(file -> file.toString().endsWith(".sql"))
                 .forEach(file -> files.add(file.toString()));
        } catch (IOException e) {
            // Do nothing
        }
        files.sort(String::compareTo);

        System.out.println(files);

        try (Connection connection = getConnection()) {
            if (connection == null) {
                throw new Exception("Connection is null");
            }

            Statement statement = connection.createStatement();
            
            for (String file : files) {
                try {
                    String sql = Files.readString(Paths.get(file));
                    statement.addBatch(sql);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            statement.executeBatch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
