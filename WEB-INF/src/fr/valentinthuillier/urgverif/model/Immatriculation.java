package fr.valentinthuillier.urgverif.model;

public class Immatriculation {
    
    public static String translator(String immatriculation) {
        return immatriculation != null ? immatriculation.replaceAll(" ", "").replaceAll("-", "").toUpperCase() : "";
    }

    public static String translateToOriginal(String immatriculation) {
        // FR941YQ -> FR-941-YQ
        String immat = translator(immatriculation);
        if(immat.length() == 7) {
            return immat.substring(0, 2) + "-" + immat.substring(2, 5) + "-" + immat.substring(5);
        }
        return null;
    }

}
