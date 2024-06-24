package fr.valentinthuillier.urgverif.model;

/**
 * ImmatriculationClass -   Cette classe permet de gérer les immatriculations en faisant des transformations
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @version 1.0
 */
public class Immatriculation {

    private Immatriculation() {
        // Permet de ne pas instancier la classe
    }
    
    /**
     * Cette méthode permet de transformer une immatriculation en une immatriculation sans espace / tiret et en majuscule
     * @param immatriculation   (String)    -   Immatriculation à transformer
     * @return String   -   Immatriculation transformée ou vide si l'immatriculation est null.
     */
    public static String translator(String immatriculation) {
        return immatriculation != null ? immatriculation.replace(" ", "").replace("-", "").toUpperCase() : "";
    }

    /**
     * Cette méthode permet de transformer une immatriculation en une immatriculation avec des espaces et des tirets
     * @param immatriculation   (String)    -   Immatriculation à transformer
     * @return String   -   Immatriculation transformée ou null si l'immatriculation n'est pas valide.
     */
    public static String translateToOriginal(String immatriculation) {
        // FR941YQ -> FR-941-YQ
        String immat = translator(immatriculation);
        if(immat.length() == 7) {
            return immat.substring(0, 2) + "-" + immat.substring(2, 5) + "-" + immat.substring(5);
        }
        return null;
    }

}
