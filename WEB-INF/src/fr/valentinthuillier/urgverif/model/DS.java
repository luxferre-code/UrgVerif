package fr.valentinthuillier.urgverif.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import fr.valentinthuillier.urgverif.Log;

/**
 * DSClass  -   Cette classe permet de gérer la connexion à la base de données
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @version 1.0
 * @see fr.valentinthuillier.urgverif.Log
 * @see java.sql.Connection
*/
public class DS {

    /**
     * Emplacement du fichier de configuration pour la connexion à la base de données
     */
    private static final String CONFIG_FILE_PATH = "/var/.config/UrgVerif/config.prop";
    private static DS instance = null;
    private final String nom;
    private final String mdp;
    private final String url;

    /**
     * Constructeur de la classe DS (private vu que singleton)
     */
    private DS() throws Exception {
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

    /**
     * Cette méthode permet d'initialiser le fichier de configuration
     * @param file  (File)  -   Fichier de configuration
     * @see java.io.File
     */
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

    /**
     * Cette méthode permet de récupérer une connexion à la base de données
     * @return  Connection  -   Connexion à la base de données
     * @see java.sql.Connection
     * @see java.sql.DriverManager
     */
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