package fr.valentinthuillier.urgverif.model.dto;

public class Materiel {
    
    private final int ID;
    private String nom;
    private int quantite;
    private Compartiment compartiment;
    private Vehicule vehicule;

    public Materiel(int iD, String nom, int quantite, Compartiment compartiment, Vehicule vehicule) {
        ID = iD;
        this.nom = nom;
        this.quantite = quantite;
        this.compartiment = compartiment;
        this.vehicule = vehicule;
    }

    public Materiel(String nom, int quantite, Compartiment compartiment, Vehicule vehicule) {
        this(-1, nom, quantite, compartiment, vehicule);
    }

    public int getID() {
        return ID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Compartiment getCompartiment() {
        return compartiment;
    }

    public void setCompartiment(Compartiment compartiment) {
        this.compartiment = compartiment;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + quantite;
        result = prime * result + ((compartiment == null) ? 0 : compartiment.hashCode());
        result = prime * result + ((vehicule == null) ? 0 : vehicule.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Materiel other = (Materiel) obj;
        if (ID != other.ID)
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (quantite != other.quantite)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Materiel [ID=" + ID + ", nom=" + nom + ", quantite=" + quantite + ", compartiment=" + compartiment
                + ", vehicule=" + vehicule + "]";
    }

    public String toHTMLLine(boolean estPresent) {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr onclick=\"window.location.href='?idMateriel=" + this.ID + "';\" class=\"" + (estPresent ? "" : "nonPresent") + "\">");
        sb.append("<td>").append(nom).append("</td>");
        sb.append("<td>").append(quantite).append("</td>");
        return sb.toString();
    }

}
