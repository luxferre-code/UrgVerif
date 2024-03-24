package fr.valentinthuillier.urgverif.model.dto;

/**
 * MaterielClass    -   Cette classe permet représenter un matériel de la base de données
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @version 1.0
 * @see fr.valentinthuillier.urgverif.model.dto.Compartiment
 * @see fr.valentinthuillier.urgverif.model.dto.Vehicule
 */
public class Materiel {
    
    private final int ID;
    private String nom;
    private int quantite;
    private Compartiment compartiment;
    private Vehicule vehicule;
    private boolean valide;

    /**
     * Constructeur de la classe Materiel
     * @param iD    (int)   -   ID du matériel
     * @param nom   (String)    -   Nom du matériel
     * @param quantite  (int)   -   Quantité du matériel
     * @param compartiment  (Compartiment)  -   Compartiment dans lequel le matériel est
     * @param vehicule  (Vehicule)  -   Véhicule dans lequel le matériel est
     * @param valide    (boolean)   -   Validité du matériel
     */
    public Materiel(int iD, String nom, int quantite, Compartiment compartiment, Vehicule vehicule, boolean valide) {
        ID = iD;
        this.nom = nom;
        this.quantite = quantite;
        this.compartiment = compartiment;
        this.vehicule = vehicule;
        this.valide = valide;
    }

    /**
     * Constructeur de la classe Materiel, ou l'ID est initialisé à -1
     * @param nom   (String)    -   Nom du matériel
     * @param quantite  (int)   -   Quantité du matériel
     * @param compartiment  (Compartiment)  -   Compartiment dans lequel le matériel est
     * @param vehicule  (Vehicule)  -   Véhicule dans lequel le matériel est
     * @param valide    (boolean)   -   Validité du matériel
     */
    public Materiel(String nom, int quantite, Compartiment compartiment, Vehicule vehicule, boolean valide) {
        this(-1, nom, quantite, compartiment, vehicule, valide);
    }

    /**
     * Getter de l'ID
     * @return  (int)   -   ID du matériel
     */
    public int getID() {
        return ID;
    }

    /**
     * Getter du nom
     * @return  (String)    -   Nom du matériel
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter du nom
     * @param nom   (String)    -   Nouveau nom du matériel
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter de la quantité
     * @return  (int)   -   Quantité du matériel
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Setter de la quantité
     * @param quantite  (int)   -   Nouvelle quantité du matériel
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Getter du compartiment
     * @return  (Compartiment)  -   Compartiment du matériel
     */
    public Compartiment getCompartiment() {
        return compartiment;
    }

    /**
     * Setter du compartiment
     * @param compartiment  (Compartiment)  -   Nouveau compartiment du matériel
     */
    public void setCompartiment(Compartiment compartiment) {
        this.compartiment = compartiment;
    }

    /**
     * Getter du véhicule
     * @return  (Vehicule)  -   Véhicule du matériel
     */
    public Vehicule getVehicule() {
        return vehicule;
    }

    /**
     * Setter du véhicule
     * @param vehicule  (Vehicule)  -   Nouveau véhicule du matériel
     */
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    /**
     * Setter de la validité
     * @param valide    (boolean)   -   Nouvelle validité du matériel
     */
    public void setValide(boolean valide) {
        this.valide = valide;
    }

    /**
     * Getter de la validité
     * @return  (boolean)   -   Validité du matériel
     */
    public boolean getValide() {
        return this.valide;
    }

    /**
     * Génération du hashcode
     * @return  (int)   -   Hashcode du matériel
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + quantite;
        result = prime * result + ((compartiment == null) ? 0 : compartiment.hashCode());
        result = prime * result + ((vehicule == null) ? 0 : vehicule.hashCode());
        result = prime * result + (valide ? 1231 : 1237);
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
        if(obj.getClass() != this.getClass()) return false;
        Materiel other = (Materiel) obj;
        return this.ID == other.ID &&
                nom != null && nom.equals(other.nom) &&
                quantite == other.quantite &&
                compartiment != null && compartiment.equals(other.compartiment) &&
                vehicule != null && vehicule.equals(other.vehicule) &&
                valide == other.valide;
    }

    /**
     * Génération de la représentation de l'objet en String
     * @return  (String)    -   Représentation de l'objet en String
     */
    @Override
    public String toString() {
        return "Materiel [ID=" + ID + ", nom=" + nom + ", quantite=" + quantite + ", compartiment=" + compartiment
                + ", vehicule=" + vehicule + ", valide=" + this.valide + "]";
    }

    /**
     * Génération de la balise HTML pour la ligne du matériel pour la vérification
     * @return  (String)    -   Balise HTML de la ligne du matériel
     */
    public String toHTMLLine() {
        String quant = this.quantite > 0 ? this.quantite + "" : "Fonctionnel";
        StringBuilder sb = new StringBuilder();
        sb.append("<tr id=\"" + this.ID + "\" class=\"" + (!this.valide ? "nonPresent verif" : "verif") + "\">");
        sb.append("<td>").append(nom).append("</td>");
        sb.append("<td>").append(quant).append("</td>");
        return sb.toString();
    }

    /**
     * Génération de la balise HTML pour la ligne du matériel pour la modification
     * @return  (String)    -   Balise HTML de la ligne du matériel
     */
    public String toHTMLModifLine() {
        String quant = this.quantite > 0 ? this.quantite + "" : "Fonctionnel";
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append("<td>").append(nom).append("</td>");
        sb.append("<td>").append(quant).append("</td>");
        sb.append("<td><button class=\"delete\" type=\"submit\" name=\"idMateriel\" value=\"").append(this.ID).append("\">Oui</button></td>");
        sb.append("</tr>");
        return sb.toString();
    }

}
