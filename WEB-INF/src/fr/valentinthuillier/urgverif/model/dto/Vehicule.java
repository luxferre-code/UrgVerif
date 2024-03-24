package fr.valentinthuillier.urgverif.model.dto;

/**
 * VehiculeClass    -   Cette classe permet de représenter une véhicule de la base de données
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @version 1.O
 */
public class Vehicule {
    
    private String immatriculation;
    private String typeEngin;
    private Centre centre;

    /**
     * Constructeur de la classe Vehicule
     * @param immatriculation   (String)    -   Immatriculation du véhicule
     * @param typeEngin (String)    -   Type d'engin du véhicule
     * @param centre    (Centre)    -   Centre du véhicule
     * @see fr.valentinthuillier.urgverif.model.dto.Centre
     */
    public Vehicule(String immatriculation, String typeEngin, Centre centre) {
        this.immatriculation = immatriculation;
        this.typeEngin = typeEngin;
        this.centre = centre;
    }

    /**
     * Getter de l'immatriculation
     * @return String   -   Immatriculation du véhicule
     */
    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * Setter de l'immatriculation
     * @param immatriculation   (String)    -   Immatriculation du véhicule
     */
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    /**
     * Getter du type d'engin
     * @return String   -   Type d'engin du véhicule
     */
    public String getTypeEngin() {
        return typeEngin;
    }

    /**
     * Setter du type d'engin
     * @param typeEngin (String)    -   Type d'engin du véhicule
     */
    public void setTypeEngin(String typeEngin) {
        this.typeEngin = typeEngin;
    }

    /**
     * Getter du centre
     * @return Centre   -   Centre du véhicule
     * @see fr.valentinthuillier.urgverif.model.dto.Centre
     */
    public Centre getCentre() {
        return centre;
    }

    /**
     * Setter du centre
     * @param centre    (Centre)    -   Centre du véhicule
     * @see fr.valentinthuillier.urgverif.model.dto.Centre
     */
    public void setCentre(Centre centre) {
        this.centre = centre;
    }

    /**
     * Génération du hashcode
     * @return int  -   Hashcode de l'objet
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((immatriculation == null) ? 0 : immatriculation.hashCode());
        result = prime * result + ((typeEngin == null) ? 0 : typeEngin.hashCode());
        result = prime * result + ((centre == null) ? 0 : centre.hashCode());
        return result;
    }

    /**
     * Vérification de l'égalité de deux objets
     * @param obj   (Object)    -   Objet à comparer
     * @return boolean  -   True si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;
        if(obj.getClass() != this.getClass()) return false;
        Vehicule other = (Vehicule) obj;
        return immatriculation != null && immatriculation.equals(other.immatriculation) &&
                typeEngin != null && typeEngin.equals(other.typeEngin) &&
                centre != null && centre.equals(other.centre);
    }

    /**
     * Génération de la représentation de l'objet en String
     * @return String   -   Représentation de l'objet en String
     */
    @Override
    public String toString() {
        return "Vehicule [immatriculation=" + immatriculation + ", typeEngin=" + typeEngin + ", centre=" + centre + "]";
    }

}
