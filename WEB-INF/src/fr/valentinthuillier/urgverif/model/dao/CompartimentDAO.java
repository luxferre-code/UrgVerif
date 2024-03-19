package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.valentinthuillier.urgverif.Log;
import fr.valentinthuillier.urgverif.model.DS;
import fr.valentinthuillier.urgverif.model.dto.Compartiment;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;

public class CompartimentDAO implements IDao<Compartiment, Integer> {

    @Override
    public Compartiment findById(Integer id) {
        Compartiment compartiment = null;
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nom, type_engin FROM compartiment WHERE id = ?");
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                compartiment = new Compartiment(id, rs.getString("nom"), rs.getString("type_engin"));
            }
            ps.close();
        } catch(Exception e) {
            Log.error("CompartimentDAO.findById: Erreur durant la récupération du compartiment: " + e.getMessage());
        }
        return compartiment;
    }

    public List<Compartiment> findAllByVehicule(Vehicule vehicule) {
        List<Compartiment> compartiments = new ArrayList<>();
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id, nom, type_engin FROM compartiment WHERE type_engin = ?");
            ps.setString(1, vehicule.getTypeEngin());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                compartiments.add(new Compartiment(rs.getInt("id"), rs.getString("nom"), rs.getString("type_engin")));
            }
            ps.close();
        } catch(Exception e) {
            Log.error("CompartimentDAO.findAllByVehicule: Erreur durant la récupération des compartiments: " + e.getMessage());
        }
        return compartiments;
    }

    @Override
    public List<Compartiment> findAll() {
        List<Compartiment> compartiments = new ArrayList<>();
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id, nom, type_engin FROM compartiment");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                compartiments.add(new Compartiment(rs.getInt("id"), rs.getString("nom"), rs.getString("type_engin")));
            }
            ps.close();
        } catch(Exception e) {
            Log.error("CompartimentDAO.findAll: Erreur durant la récupération des compartiments: " + e.getMessage());
        }
        return compartiments;
    }

    @Override
    public Compartiment save(Compartiment dto) {
        Compartiment compartiment = null;
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO compartiment(nom, type_engin) VALUES (?, ?) RETURNING id");
            ps.setString(1, dto.getNom());
            ps.setString(2, dto.getTypeEngin());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                compartiment = new Compartiment(rs.getInt(1), dto.getNom(), dto.getTypeEngin());
            }
        } catch(Exception e) {
            Log.error("CompartimentDAO.save: Erreur durant l'ajout du compartiment: " + e.getMessage());
        }
        return compartiment;   
    }

    @Override
    public Compartiment update(Compartiment dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE compartiment SET nom = ?, type_en WHERE id = ?");
            ps.setString(1, dto.getNom());
            ps.setInt(2, dto.getID());
            ps.executeUpdate();
        } catch(Exception e) {
            Log.error("CompartimentDAO.update: Erreur durant la mise à jour du compartiment: " + e.getMessage());
        }
        return dto;   
    }

    @Override
    public boolean delete(Compartiment dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM compartiment WHERE id = ?");
            ps.setInt(1, dto.getID());
            return ps.executeUpdate() > 0;
        } catch(Exception e) {
            Log.error("CompartimentDAO.delete: Erreur durant la suppression du compartiment: " + e.getMessage());
        }
        return false;
    }
    
    

}
