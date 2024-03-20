package fr.valentinthuillier.urgverif;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    public static boolean logToFile = true;
    public static boolean logToConsole = true;

    private static final String APP_STRING = "UrgVerif";
    private static final String INFO_STRING = "INFO";
    private static final String ERROR_STRING = "ERROR";
    private static final String WARNING_STRING = "WARNING";
    private static final String LOG_FORMAT = "[%s] %s: %s";
    private static final String LOG_DIR_FILE = "/opt/tomcat/webapps/urgverif/logs/urgverif-" + new SimpleDateFormat().format(new Date()) + ".log";

    private static void log(String log) {
        if(logToConsole) System.out.println(log);
        if(logToFile) writeInFile(log);
    }

    public static void info(String message) {
        log(String.format(LOG_FORMAT, APP_STRING, INFO_STRING, message));
    }

    public static void error(String message) {
        log(String.format(LOG_FORMAT, APP_STRING, ERROR_STRING, message));
    }

    public static void warning(String message) {
        log(String.format(LOG_FORMAT, APP_STRING, WARNING_STRING, message));

    }

    private static void writeInFile(String s) {
        File f = new File(LOG_DIR_FILE);
        if(!f.exists()) {
            try {
                new File(f.getParent()).mkdirs();
                f.createNewFile();
            }
            catch(Exception e) {
                logToFile = false;
                error("Erreur lors de la création du fichier de log : " + f.getAbsolutePath());
            }
        }
        try {
            FileWriter fw = new FileWriter(f, true);
            fw.write(s + "\n");
            fw.close();
        }
        catch(Exception e) {
            logToFile = false;
            error("Erreur lors de l'écriture dans le fichier de log");
        }
    }
    
}
