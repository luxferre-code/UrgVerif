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

public class CentreDAO implements IDao<Centre, Integer> {

    @Override
    public Centre findById(Integer id) {
        Centre centre = null;
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nom, adresse, chef_centre, telephone FROM centre WHERE id = ?");
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                centre = new Centre(id, rs.getString("nom"), rs.getString("adresse"), rs.getString("chef_centre"), rs.getString("telephone"));
            }
        } catch(Exception e) {
            Log.error("CentreDAO.findById: Erreur durant la récupération du centre: " + e.getMessage());
        }
        return centre;
    }

    @Override
    public List<Centre> findAll() {
        List<Centre> centres = new ArrayList<>();
        try(Connection con = DS.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nom, adresse, chef_centre, telephone FROM centre");
            while(rs.next()) {
                centres.add(new Centre(rs.getInt("id"), rs.getString("nom"), rs.getString("adresse"), rs.getString("chef_centre"), rs.getString("telephone")));
            }
        } catch(Exception e) {
            Log.error("CentreDAO.findAll: Erreur durant la récupération des centres: " + e.getMessage());
        }
        return centres;
    }

    @Override
    public Centre save(Centre dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO centre(nom, adresse, chef_centre, telephone) VALUES(?, ?, ?, ?)");
            ps.setString(1, dto.getNom());
            ps.setString(2, dto.getAdresse());
            ps.setString(3, dto.getChefCentre());
            ps.setString(4, dto.getTelephone());
            ps.executeUpdate();
            int id = ps.getGeneratedKeys().getInt(1);
            return new Centre(id, dto.getNom(), dto.getAdresse(), dto.getChefCentre(), dto.getTelephone());
        } catch(Exception e) {
            Log.error("CentreDAO.save: Erreur lors de l'ajout du centre: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Centre update(Centre dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE centre SET nom = ?, adresse = ?, chef_centre = ?, telephone = ? WHERE id = ?");
            ps.setString(1, dto.getNom());
            ps.setString(2, dto.getAdresse());
            ps.setString(3, dto.getChefCentre());
            ps.setString(4, dto.getTelephone());
            ps.setInt(5, dto.getID());
            ps.executeUpdate();
            return dto;
        } catch(Exception e) {
            Log.error("CentreDAO.update: Erreur lors de la mise à jour du centre: " + e.getMessage());
        }
        return null;
    }

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
