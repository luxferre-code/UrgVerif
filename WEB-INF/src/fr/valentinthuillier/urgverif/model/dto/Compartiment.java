package fr.valentinthuillier.urgverif.model.dto;

/**
 * CompartimentClass -   Cette classe permet de représenter un compartiment de la base de données
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 */
public class Compartiment {
    
    private final int ID;
    private String nom;
    private String typeEngin;

    /**
     * Constructeur de la classe Compartiment
     * @param iD    (int)   -   ID du compartiment
     * @param nom   (String)    -   Nom du compartiment
     * @param typeEngin (String)    -   Type d'engin du compartiment
     */
    public Compartiment(int iD, String nom, String typeEngin) {
        ID = iD;
        this.nom = nom;
        this.typeEngin = typeEngin;
    }

    /**
     * Constructeur de la classe Compartiment, ou l'ID est initialisé à -1
     * @param nom   (String)    -   Nom du compartiment
     * @param typeEngin (String)    -   Type d'engin du compartiment
     */
    public Compartiment(String nom, String typeEngin) {
        this(-1, nom, typeEngin);
    }

    /**
     * Getter de l'ID
     * @return  (int)   -   ID du compartiment
     */
    public int getID() {
        return ID;
    }

    /**
     * Getter du nom
     * @return  (String)    -   Nom du compartiment
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter du nom
     * @param nom   (String)    -   Nom du compartiment
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter du type d'engin
     * @return  (String)    -   Type d'engin du compartiment
     */
    public String getTypeEngin() {
        return typeEngin;
    }

    /**
     * Setter du type d'engin
     * @param typeEngin (String)    -   Type d'engin du compartiment
     */
    public void setTypeEngin(String typeEngin) {
        this.typeEngin = typeEngin;
    }

    /**
     * Génération du hashcode
     * @return  (int)   -   Hashcode de l'objet
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((typeEngin == null) ? 0 : typeEngin.hashCode());
        return result;
    }

    /**
     * Comparaison de deux objets
     * @param obj   (Object)    -   Objet à comparer
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Compartiment other = (Compartiment) obj;
        return ID == other.ID &&
                nom != null && nom.equals(other.nom) &&
                typeEngin != null && typeEngin.equals(other.typeEngin);
    }

    /**
     * Génération de la représentation textuelle de l'objet
     * @return  (String)    -   Représentation textuelle de l'objet
     */
    @Override
    public String toString() {
        return "Compartiment [ID=" + ID + ", nom=" + nom + ", typeEngin=" + typeEngin + "]";
    }

}
