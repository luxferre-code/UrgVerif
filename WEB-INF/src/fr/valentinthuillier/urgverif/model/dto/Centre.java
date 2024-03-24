package fr.valentinthuillier.urgverif.model.dto;

/**
 * CentreClass -   Cette classe permet de représenter un centre de la base de données
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @version 1.0
 */
public class Centre {
    
    private final int ID;
    private String nom;
    private String adresse;
    private String chefCentre;
    private String telephone;
    
    /**
     * Constructeur de la classe Centre
     * @param iD    (int)   -   ID du centre
     * @param nom   (String)    -   Nom du centre
     * @param adresse   (String)    -   Adresse du centre
     * @param chefCentre    (String)    -   Chef du centre
     * @param telephone (String)    -   Téléphone du centre
     */
    public Centre(int iD, String nom, String adresse, String chefCentre, String telephone) {
        ID = iD;
        this.nom = nom;
        this.adresse = adresse;
        this.chefCentre = chefCentre;
        this.telephone = telephone;
    }

    /**
     * Constructeur de la classe Centre, ou l'ID est initialisé à -1
     * @param nom   (String)    -   Nom du centre
     * @param adresse   (String)    -   Adresse du centre
     * @param chefCentre    (String)    -   Chef du centre
     * @param telephone (String)    -   Téléphone du centre
     */
    public Centre(String nom, String adresse, String chefCentre, String telephone) {
        this(-1, nom, adresse, chefCentre, telephone);
    }

    /**
     * Getter de l'ID
     * @return  (int)   -   ID du centre
     */
    public int getID() {
        return ID;
    }

    /**
     * Getter du nom
     * @return  (String)    -   Nom du centre
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter du nom
     * @param nom   (String)    -   Nom du centre
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter de l'adresse
     * @return  (String)    -   Adresse du centre
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Setter de l'adresse
     * @param adresse   (String)    -   Adresse du centre
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Getter du chef du centre
     * @return  (String)    -   Chef du centre
     */
    public String getChefCentre() {
        return chefCentre;
    }

    /**
     * Setter du chef du centre
     * @param chefCentre    (String)    -   Chef du centre
     */
    public void setChefCentre(String chefCentre) {
        this.chefCentre = chefCentre;
    }

    /**
     * Getter du téléphone
     * @return  (String)    -   Téléphone du centre
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter du téléphone
     * @param telephone (String)    -   Téléphone du centre
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Génération du hashcode
     * @return  (int)   -   Hashcode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
        result = prime * result + ((chefCentre == null) ? 0 : chefCentre.hashCode());
        result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
        return result;
    }

    /**
     * Comparaison de deux objets
     * @param obj   (Object)    -   Objet à comparer
     * @return  (boolean)   -   True si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;
        if(obj.getClass() != this.getClass()) return false;
        Centre other = (Centre) obj;
        return ID == other.ID &&
                nom != null && nom.equals(other.nom) &&
                adresse != null && adresse.equals(other.adresse) &&
                chefCentre != null && chefCentre.equals(other.chefCentre) &&
                telephone != null && telephone.equals(other.telephone);
    }

    

}
