package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.valentinthuillier.urgverif.Log;
import fr.valentinthuillier.urgverif.model.DS;
import fr.valentinthuillier.urgverif.model.dto.Centre;

/**
 * CentreDAOClass -   Cette classe permet de manipuler les centres au sein de la base de données.
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @version 1.0
 * @see fr.valentinthuillier.urgverif.model.dto.Centre
 * @see fr.valentinthuillier.urgverif.model.DS
 * @see fr.valentinthuillier.urgverif.Log
*/
public class CentreDAO implements IDao<Centre, Integer> {

    /**
     * Cette méthode permet de chercher par son identifiant un centre.
     * @param   id  (Integer)   -   Son identifiant
     * @return  (Centre)  -   L'objet représentant le centre ou null si l'identifiant n'est pas enregistré.
     */
    @Override
    public Centre findById(Integer id) {
        Centre centre = null;
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nom, adresse, telephone FROM centre WHERE id = ?");
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                centre = new Centre(id, rs.getString("nom"), rs.getString("adresse"), rs.getString("telephone"));
            }
        } catch(Exception e) {
            Log.error("CentreDAO.findById: Erreur durant la récupération du centre: " + e.getMessage());
        }
        return centre;
    }

    /**
     * Cette méthode permet d'obtenir tous les centres présent dans la base de données.
     * @return  (List<Centre>)  -   La liste des centres.
     */
    @Override
    public List<Centre> findAll() {
        List<Centre> centres = new ArrayList<>();
        try(Connection con = DS.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nom, adresse, telephone FROM centre");
            while(rs.next()) {
                centres.add(new Centre(rs.getInt("id"), rs.getString("nom"), rs.getString("adresse"), rs.getString("telephone")));
            }
        } catch(Exception e) {
            Log.error("CentreDAO.findAll: Erreur durant la récupération des centres: " + e.getMessage());
        }
        return centres;
    }

    /**
     * Cette méthode permet d'ajouter un centre dans la base de données.
     * @param   dto (Centre)    -   L'objet représentant le centre à ajouter.
     * @return  (Centre)    -   L'objet représentant le centre ajouté ou null si une erreur est survenue.
     */
    @Override
    public Centre save(Centre dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO centre(nom, adresse telephone) VALUES(?, ?, ?)");
            ps.setString(1, dto.getNom());
            ps.setString(2, dto.getAdresse());
            ps.setString(3, dto.getTelephone());
            ps.executeUpdate();
            int id = ps.getGeneratedKeys().getInt(1);
            return new Centre(id, dto.getNom(), dto.getAdresse(), dto.getTelephone());
        } catch(Exception e) {
            Log.error("CentreDAO.save: Erreur lors de l'ajout du centre: " + e.getMessage());
        }
        return null;
    }

    /**
     * Cette méthode permet de mettre à jour un centre dans la base de données.
     * @param   dto (Centre)    -   L'objet représentant le centre à mettre à jour.
     * @return  (Centre)    -   L'objet représentant le centre mis à jour ou null si une erreur est survenue.
     */
    @Override
    public Centre update(Centre dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE centre SET nom = ?, adresse = ?, telephone = ? WHERE id = ?");
            ps.setString(1, dto.getNom());
            ps.setString(2, dto.getAdresse());
            ps.setString(3, dto.getTelephone());
            ps.setInt(4, dto.getID());
            ps.executeUpdate();
            return dto;
        } catch(Exception e) {
            Log.error("CentreDAO.update: Erreur lors de la mise à jour du centre: " + e.getMessage());
        }
        return null;
    }

    /**
     * Cette méthode permet de supprimer un centre de la base de données.
     * @param   dto (Centre)    -   L'objet représentant le centre à supprimer.
     * @return  (boolean)   -   true si la suppression a été effectuée, false sinon.
     */
    @Override
    public boolean delete(Centre dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM centre WHERE id = ?");
            ps.setInt(1, dto.getID());
            return ps.executeUpdate() > 0;
        } catch(Exception e) {
            Log.error("CentreDAO.delete: Erreur lors de la suppression du centre: " + e.getMessage());
        }
        return false;
    }
    
}
