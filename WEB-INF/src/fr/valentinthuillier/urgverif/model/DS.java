package fr.valentinthuillier.urgverif.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import fr.valentinthuillier.urgverif.Log;

public class DS {

    private static final String CONFIG_FILE_PATH = "/var/.config/UrgVerif/config.prop";
    private static DS instance = null;
    private final String nom;
    private final String mdp;
    private final String url;


    public DS() throws Exception {
        Log.info("Loading database configuration");
        Properties p = new Properties();

        /* Check if config file exist */
        File file = new File(CONFIG_FILE_PATH);
        if(!file.exists()) {
            this.initConfigFile(file);
        }
        p.load(new FileInputStream(file));
        Class.forName(p.getProperty("driver"));
        this.url = p.getProperty("url");
        this.nom = p.getProperty("login");
        this.mdp = p.getProperty("password");
    }

    private void initConfigFile(File file) {
        try {
            try { file.getParentFile().mkdirs(); } catch(Exception e) {}
            try { file.createNewFile(); } catch(Exception e) {}
            Properties p = new Properties();
            p.setProperty("driver", "org.postgresql.Driver");
            p.setProperty("url", "jdbc:postgresql://valentin-thuillier.fr:5432/urgverif");
            p.setProperty("login", "urgverif");
            p.setProperty("password", "TOSET");
            p.store(new FileOutputStream(file), "UrgVerif configuration file - Just set the password");
            Log.info("Config file created, please modify at " + file.getAbsolutePath() + " and restart the server.");
        } catch(Exception e) {
            Log.error("Couldn't create config file, please check your permissions and restart the server.");
            System.exit(1);
        }
    }

    public static Connection getConnection() {
        try {
            if(instance == null)
                instance = new DS();
            return DriverManager.getConnection(instance.url, instance.nom, instance.mdp);
        } catch(Exception e) {
            Log.error("Couldn't connect to database, please check your configuration file at " + new File(CONFIG_FILE_PATH).getAbsolutePath() + " and restart the server.");
        }
        return null;
    }


}