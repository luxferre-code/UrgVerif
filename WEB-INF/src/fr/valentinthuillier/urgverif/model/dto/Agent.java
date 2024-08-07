package fr.valentinthuillier.urgverif.model.dto;

public class Agent {

    private String matricule;
    private String nom;
    private String prenom;
    private String password;
    private String mail;
    private int idCentre;
    private Gallon gallon;

    public Agent(String matricule, String nom, String prenom, String password, String mail, int idCentre, Gallon gallon) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.mail = mail;
        this.idCentre = idCentre;
        this.gallon = gallon;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getIdCentre() {
        return idCentre;
    }

    public void setIdCentre(int idCentre) {
        this.idCentre = idCentre;
    }

    public Gallon getGallon() {
        return gallon;
    }

    public void setGallon(Gallon gallon) {
        this.gallon = gallon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    
    
    @Override
    public String toString() {
        return this.gallon.getGrade() + " " + this.nom.toUpperCase() + " " + this.prenom.substring(0, 1).toUpperCase() + ". (" + this.matricule + ")";
    }

}
