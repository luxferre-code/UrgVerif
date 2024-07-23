package fr.valentinthuillier.urgverif.model.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;

import fr.valentinthuillier.urgverif.Log;
import fr.valentinthuillier.urgverif.model.DS;

/**
 * Enumeration representing all types of fire engines that can be used by fire departments.
 * This includes common vehicles such as the VSAV (Rescue and Assistance Vehicle for Victims)
 * and FPT (Fire Pump Truck), as well as more specialized vehicles.
 * 
 * Each type of fire engine is associated with its full name.
 */
public enum TypeEngin {

    VSAV("Véhicule de Secours et d'Assistance aux Victimes"),
    FPT("Fourgon Pompe-Tonne"),
    VSR("Véhicule de Secours Routier"),
    EPA("Echelle Pivotante Automatique"),
    CCF("Camion Citerne Feux de Forêt"),
    BRS("Bateau de Reconnaissance et de Sauvetage"),
    FSR("Fourgon de Secours Routier"),
    VID("Véhicule d'Intervention Divers"),
    VL("Véhicule Léger"),
    CCRM("Camion Citerne Rural Moyen"),
    CCFM("Camion Citerne Feux de Forêt Moyen"),
    EPS("Echelle Pivotante Semi-automatique"),
    EPC("Echelle Pivotante Combinée"),
    FPTL("Fourgon Pompe-Tonne Léger"),
    FPTLHR("Fourgon Pompe-Tonne Léger Haute-Régime"),
    FPTGP("Fourgon Pompe-Tonne Grande Puissance"),
    VPI("Véhicule de Première Intervention"),
    VLHR("Véhicule Léger Hors Route"),
    FSRLHR("Fourgon de Secours Routier Léger Hors Route"),
    FSRM("Fourgon de Secours Routier Moyen"),
    VSM("Véhicule de Secours Médicalisé"),
    VPMA("Véhicule de Premier Maître d'Appel"),
    FCYM("Fourgon Citerne de Moyenne capacité"),
    VIDP("Véhicule d'Intervention Divers Polyvalent"),
    VTUT("Véhicule Tout Usage Tout Terrain"),
    VTUTP("Véhicule Tout Usage Tout Terrain Polyvalent"),
    VTU("Véhicule Tout Usage"),
    VFI("Véhicule de Formation Initiale"),
    VLI("Véhicule Léger d'Intervention"),
    VLPCHR("Véhicule Léger de Premier Chef de Rang"),
    VPC("Véhicule de Premier Chef"),
    CEDEC("Camion d'Engin de Décontamination"),
    VLCG("Véhicule de Liaison et de Commandement Général"),
    VLCGR("Véhicule de Liaison et de Commandement Régional"),
    VLM("Véhicule Léger Médicalisé"),
    VLSR("Véhicule Léger de Secours Routier"),
    VLTT("Véhicule Léger Tout Terrain"),
    VTULE("Véhicule Tout Usage Léger Équipé"),
    VTP("Véhicule de Transport de Personnel"),
    VTPR("Véhicule de Transport de Personnel Renforcé"),
    VLR("Véhicule Léger de Ravitaillement"),
    VLRM("Véhicule Léger de Ravitaillement Médicalisé"),
    VLC("Véhicule Léger de Commandement"),
    VLH("Véhicule Léger Hydraulique"),
    VLHR2("Véhicule Léger Hors Route 2"),
    VLHSR("Véhicule Léger Hors Secours Routier"),
    VLHSC("Véhicule Léger Hors Secours Combiné"),
    VLRG("Véhicule Léger de Reconnaissance Général"),
    VLRGP("Véhicule Léger de Reconnaissance Général Polyvalent"),
    VLCS("Véhicule Léger de Commandement de Secteur"),
    VLHSP("Véhicule Léger Hors Secours de Premier Niveau"),
    VLHSG("Véhicule Léger Hors Secours Général"),
    VLS("Véhicule Léger de Surveillance"),
    VLTL("Véhicule Léger de Transport Logistique"),
    VLTR("Véhicule Léger de Transport Routier"),
    VLTTG("Véhicule Léger Tout Terrain Grand Modèle"),
    VLCC("Véhicule Léger de Commandement et de Coordination"),
    VLCPC("Véhicule Léger de Commandement Principal de Coordination");

    private final String fullName;

    TypeEngin(String fullName) {
        this.fullName = fullName;
		updateBDD(); // Update the database with the new type of fire engine if it does not already exist
    }

    public String getFullName() {
        return fullName;
    }

	private void updateBDD() {
		try(Connection con = DS.getConnection()) {
			PreparedStatement ps = con.prepareStatement("INSERT INTO type_engin(nom) VALUES (?)");
			ps.setString(1, this.name());
			ps.executeUpdate();
			ps.close();
		} catch(Exception e) {
			Log.error("Erreur lors de la mise à jour de la base de données: " + e.getMessage());
		}
	}

}
