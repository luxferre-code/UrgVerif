package fr.valentinthuillier.urgverif.model.dto;

public class Compartiment {
    
    private final int ID;
    private String nom;

    public Compartiment(int iD, String nom) {
        ID = iD;
        this.nom = nom;
    }

    public Compartiment(String nom) {
        this(-1, nom);
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
        Compartiment other = (Compartiment) obj;
        if (ID != other.ID)
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Compartiment [ID=" + ID + ", nom=" + nom + "]";
    }

}
