package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.valentinthuillier.urgverif.model.DS;
import fr.valentinthuillier.urgverif.model.dto.Compartiment;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;

public class CompartimentDAO implements IDao<Compartiment, Integer> {

    @Override
    public Compartiment findById(Integer id) {
        Compartiment compartiment = null;
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nom FROM compartiment WHERE id = ?");
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                compartiment = new Compartiment(id, rs.getString("nom"));
            }
            ps.close();
        } catch(Exception e) {
            System.out.println("CompartimentDAO.findById");
            System.out.println(e.getMessage());
        }
        return compartiment;
    }

    public List<Compartiment> findAllByVehicule(Vehicule vehicule) {
        List<Compartiment> compartiments = new ArrayList<>();
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT c.id, c.nom FROM compartiment c JOIN materiel m ON c.id = m.id_compartiment WHERE m.id_vehicule = ?");
            ps.setString(1, vehicule.getImmatriculation());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                compartiments.add(new Compartiment(rs.getInt("id"), rs.getString("nom")));
            }
            ps.close();
        } catch(Exception e) {
            System.out.println("CompartimentDAO.findAllByVehicule");
            System.out.println(e.getMessage());
        }
        return compartiments;
    }

    @Override
    public List<Compartiment> findAll() {
        List<Compartiment> compartiments = new ArrayList<>();
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id, nom FROM compartiment");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                compartiments.add(new Compartiment(rs.getInt("id"), rs.getString("nom")));
            }
            ps.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return compartiments;
    }

    @Override
    public Compartiment save(Compartiment dto) {
        Compartiment compartiment = null;
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO compartiment(nom) VALUES(?)");
            ps.setString(1, dto.getNom());
            ps.executeUpdate();
            int id = ps.getGeneratedKeys().getInt(1);
            compartiment = new Compartiment(id, dto.getNom());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return compartiment;   
    }

    @Override
    public Compartiment update(Compartiment dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE compartiment SET nom = ? WHERE id = ?");
            ps.setString(1, dto.getNom());
            ps.setInt(2, dto.getID());
            ps.executeUpdate();
        } catch(Exception e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    

}
