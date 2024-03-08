package fr.valentinthuillier.urgverif.model.dto;

public class Centre {
    
    private final int ID;
    private String nom;
    private String adresse;
    private String chefCentre;
    private String telephone;
    
    public Centre(int iD, String nom, String adresse, String chefCentre, String telephone) {
        ID = iD;
        this.nom = nom;
        this.adresse = adresse;
        this.chefCentre = chefCentre;
        this.telephone = telephone;
    }

    public Centre(String nom, String adresse, String chefCentre, String telephone) {
        this(-1, nom, adresse, chefCentre, telephone);
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getChefCentre() {
        return chefCentre;
    }

    public void setChefCentre(String chefCentre) {
        this.chefCentre = chefCentre;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Centre other = (Centre) obj;
        if (ID != other.ID)
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (adresse == null) {
            if (other.adresse != null)
                return false;
        } else if (!adresse.equals(other.adresse))
            return false;
        if (chefCentre == null) {
            if (other.chefCentre != null)
                return false;
        } else if (!chefCentre.equals(other.chefCentre))
            return false;
        if (telephone == null) {
            if (other.telephone != null)
                return false;
        } else if (!telephone.equals(other.telephone))
            return false;
        return true;
    }

    

}
