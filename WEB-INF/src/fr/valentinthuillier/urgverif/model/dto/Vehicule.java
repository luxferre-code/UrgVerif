package fr.valentinthuillier.urgverif.model.dto;

public class Vehicule {
    
    private String immatriculation;
    private String typeEngin;
    private Centre centre;

    public Vehicule(String immatriculation, String typeEngin, Centre centre) {
        this.immatriculation = immatriculation;
        this.typeEngin = typeEngin;
        this.centre = centre;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getTypeEngin() {
        return typeEngin;
    }

    public void setTypeEngin(String typeEngin) {
        this.typeEngin = typeEngin;
    }

    public Centre getCentre() {
        return centre;
    }

    public void setCentre(Centre centre) {
        this.centre = centre;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((immatriculation == null) ? 0 : immatriculation.hashCode());
        result = prime * result + ((typeEngin == null) ? 0 : typeEngin.hashCode());
        result = prime * result + ((centre == null) ? 0 : centre.hashCode());
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
        Vehicule other = (Vehicule) obj;
        if (immatriculation == null) {
            if (other.immatriculation != null)
                return false;
        } else if (!immatriculation.equals(other.immatriculation))
            return false;
        if (typeEngin == null) {
            if (other.typeEngin != null)
                return false;
        } else if (!typeEngin.equals(other.typeEngin))
            return false;
        if (centre == null) {
            if (other.centre != null)
                return false;
        } else if (!centre.equals(other.centre))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vehicule [immatriculation=" + immatriculation + ", typeEngin=" + typeEngin + ", centre=" + centre + "]";
    }

}
