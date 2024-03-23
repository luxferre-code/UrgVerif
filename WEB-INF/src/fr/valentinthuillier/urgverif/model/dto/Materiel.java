package fr.valentinthuillier.urgverif.model.dto;

public class Materiel {
    
    private final int ID;
    private String nom;
    private int quantite;
    private Compartiment compartiment;
    private Vehicule vehicule;
    private boolean valide;

    public Materiel(int iD, String nom, int quantite, Compartiment compartiment, Vehicule vehicule, boolean valide) {
        ID = iD;
        this.nom = nom;
        this.quantite = quantite;
        this.compartiment = compartiment;
        this.vehicule = vehicule;
        this.valide = valide;
    }

    public Materiel(String nom, int quantite, Compartiment compartiment, Vehicule vehicule, boolean valide) {
        this(-1, nom, quantite, compartiment, vehicule, valide);
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

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public boolean getValide() {
        return this.valide;
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
        result = prime * result + (valide ? 1231 : 1237);
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
        return this.valide == other.valide;
    }

    @Override
    public String toString() {
        return "Materiel [ID=" + ID + ", nom=" + nom + ", quantite=" + quantite + ", compartiment=" + compartiment
                + ", vehicule=" + vehicule + ", valide=" + this.valide + "]";
    }

    public String toHTMLLine() {
        String quant = this.quantite > 0 ? this.quantite + "" : "Fonctionnel";
        StringBuilder sb = new StringBuilder();
        sb.append("<tr id=\"" + this.ID + "\" class=\"" + (!this.valide ? "nonPresent verif" : "verif") + "\">");
        sb.append("<td>").append(nom).append("</td>");
        sb.append("<td>").append(quant).append("</td>");
        return sb.toString();
    }

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
