package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.valentinthuillier.urgverif.Log;
import fr.valentinthuillier.urgverif.model.DS;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;

/**
 * VehiculeDAOClass -   Cette classe permet de manipuler les véhicules au sein de la base de données.
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @see fr.valentinthuillier.urgverif.model.dto.Vehicule
 * @see fr.valentinthuillier.urgverif.model.DS
 * @see fr.valentinthuillier.urgverif.Log
 */
public class VehiculeDAO implements IDao<Vehicule, String> {

    /**
     * Cette méthode permet de chercher par sa plaque d'immatriculation un véhicule.
     * @param   id  (String)   -   Sa plaque d'immatriculation
     * @return  (Vehicule)  -   L'objet représentant le véhicule ou null si la plaque n'est pas enregistré.
     */
    @Override
    public Vehicule findById(String id) {
        Vehicule vehicule = null;
        try(Connection con = DS.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT immatriculation, type_engin, id_centre FROM vehicule WHERE immatriculation = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                CentreDAO centreDAO = new CentreDAO();
                vehicule = new Vehicule(rs.getString("immatriculation"), rs.getString("type_engin"), centreDAO.findById(rs.getInt("id_centre")));
            }
            ps.close();

        } catch(Exception e) {
            Log.error("Erreur lors de la récupération du véhicule: " + e.getMessage());
        }
        return vehicule;
    }

    /**
     * Cette méthode permet d'obtenir tous les types d'engins présent dans la base de données.
     * @return  (List<String>)  -   La liste des types d'engins.
     */
    public List<String> findAllTypeEngin() {
        List<String> typeEngin = new ArrayList<>();
        try(Connection con = DS.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM type_engin");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                typeEngin.add(rs.getString("nom"));
            }
            ps.close();

        } catch(Exception e) {
            Log.error("Erreur lors de la récupération des types d'engin: " + e.getMessage());
        }
        return typeEngin;
    }

    public List<Vehicule> findByCentre(int id) {
        List<Vehicule> vehicules = new ArrayList<>();
        try(Connection con = DS.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT immatriculation, type_engin FROM vehicule WHERE id_centre = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                vehicules.add(new Vehicule(rs.getString("immatriculation"), rs.getString("type_engin"), null));
            }
            ps.close();

        } catch(Exception e) {
            Log.error("Erreur lors de la récupération des véhicules: " + e.getMessage());
        }
        return vehicules;
    }

    @Override
    public List<Vehicule> findAll() {
        Log.warning("Unimplemented method 'findAll' in VehiculeDAO");
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    /**
     * Cette méthode permet d'enregistrer un véhicule dans la base de données.
     * @param   dto (Vehicule)  -   L'objet représentant le véhicule à enregistrer
     * @return  (Vehicule)  -   L'objet représentant le véhicule enregistré
     */
    @Override
    public Vehicule save(Vehicule dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO vehicule(immatriculation, type_engin, id_centre) VALUES(?, ?, ?)");
            ps.setString(1, dto.getImmatriculation());
            ps.setString(2, dto.getTypeEngin().name());
            ps.setInt(3, dto.getCentre().getID());
            ps.executeUpdate();
            ps.close();
            return new Vehicule(dto.getImmatriculation(), dto.getTypeEngin().name(), dto.getCentre());
        } catch(Exception e) {
            Log.error("Erreur lors de l'ajout du véhicule: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Vehicule update(Vehicule dto) {
        Log.warning("Unimplemented method 'update' in VehiculeDAO");
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(Vehicule dto) {
        Log.warning("Unimplemented method 'delete' in VehiculeDAO");
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    /**
     * Cette méthode permet d'affecter le matériel par défaut dans le véhciule.
     * @param v (Vehicule)  -   Le véhicule à qui affecter le matériel
     */
    public void affect(Vehicule v) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO materiel(id_compartiment, id_vehicule, nom, quantite) SELECT id_compartiment, ?, nom, quantite FROM materiel WHERE id_vehicule = ?");
            ps.setString(1, v.getImmatriculation());
            ps.setString(2, v.getTypeEngin().name().toLowerCase());
            ps.executeUpdate();
            ps.close();
        } catch(Exception e) {
            Log.error("Erreur lors de l'affectation du matériel: " + e.getMessage());
        }
    }

    public boolean isVerifiedToday(Vehicule v) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM verif_history WHERE immatriculation = ? AND date_verif = CURRENT_DATE");
            ps.setString(1, v.getImmatriculation());
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                return false;
            }
            ps.close();
            return true;
        } catch(Exception e) {
            Log.error("Erreur lors de la vérification de la vérification: " + e.getMessage());
        }
        return false;
    }

}
