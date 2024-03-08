package fr.valentinthuillier.urgverif.model.dto;

public class Compartiment {
    
    private final int ID;
    private String nom;
    private String typeEngin;

    public Compartiment(int iD, String nom, String typeEngin) {
        ID = iD;
        this.nom = nom;
        this.typeEngin = typeEngin;
    }

    public Compartiment(String nom, String typeEngin) {
        this(-1, nom, typeEngin);
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

    public String getTypeEngin() {
        return typeEngin;
    }

    public void setTypeEngin(String typeEngin) {
        this.typeEngin = typeEngin;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((nom == null) ? 0 : nom.hashCode());
        result = prime * result + ((typeEngin == null) ? 0 : typeEngin.hashCode());
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
        if (typeEngin == null) {
            if (other.typeEngin != null)
                return false;
        } else if (!typeEngin.equals(other.typeEngin))
            return false;
        return true;
    }

    

}
