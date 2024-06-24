package fr.valentinthuillier.urgverif.model.dto;

public enum Gallon {
    SAPEUR_2EME_CLASSE(1, "Sapeur 2ème classe"),
    SAPEUR_1ERE_CLASSE(2, "Sapeur 1ère classe"),
    CAPORAL(3, "Caporal"),
    CAPORAL_CHEF(4, "Caporal-chef"),
    SERGENT(5, "Sergent"),
    SERGENT_CHEF(6, "Sergent-chef"),
    ADJUDANT(7, "Adjudant"),
    ADJUDANT_CHEF(8, "Adjudant-chef"),
    LIEUTENANT(9, "Lieutenant"),
    CAPITAINE(10, "Capitaine"),
    COMMANDANT(11, "Commandant"),
    LIEUTENANT_COLONEL(12, "Lieutenant-colonel"),
    COLONEL(13, "Colonel"),
    ADMINISTRATEUR(14, "Administrateur");

    private final int ID;
    private final String grade;

    Gallon(int id, String grade) {
        this.ID = id;
        this.grade = grade;
    }

    public int getID() {
        return ID;
    }

    public String getGrade() {
        return grade;
    }

    public static Gallon getGallon(int id) {
        for(Gallon g : Gallon.values()) {
            if(g.getID() == id) {
                return g;
            }
        }
        return null;
    }

}
