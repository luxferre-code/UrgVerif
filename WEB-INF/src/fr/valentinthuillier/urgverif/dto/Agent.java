package fr.valentinthuillier.urgverif.dto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Agent {

    private static final Log log = LogFactory.getLog(Agent.class);

    private final String matricule;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String number;
    private final String hashedPassword;

    public Agent(String matricule, String firstName, String lastName, String email, String number, String hashedPassword) {
        log.info("Creating agent with matricule: " + matricule);
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.hashedPassword = hashedPassword;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "matricule='" + matricule + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                '}';
    }
}
