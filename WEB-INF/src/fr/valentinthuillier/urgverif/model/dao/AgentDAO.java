package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.valentinthuillier.urgverif.Log;
import fr.valentinthuillier.urgverif.model.DS;
import fr.valentinthuillier.urgverif.model.dto.Agent;
import fr.valentinthuillier.urgverif.model.dto.Gallon;

public class AgentDAO implements IDao<Agent, String> {

    @Override
    public Agent findById(String matricule) {
        Agent agent = null;
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nom, prenom, password, mail, id_centre, gallon FROM agent WHERE matricule = ?");
            ps.setString(1, matricule);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                agent = new Agent(
                    matricule,
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("mail"),
                    rs.getInt("id_centre"),
                    Gallon.getGallon(rs.getInt("gallon"))
                );
            }
        } catch(Exception e) {
            Log.error(e.getMessage());
        }
        return agent;
    }

    @Override
    public List<Agent> findAll() {
        List<Agent> agents = new ArrayList<>();
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT matricule, nom, prenom, mail, id_centre, gallon FROM agent");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                agents.add(new Agent(
                    rs.getString("matricule"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("mail"),
                    rs.getInt("id_centre"),
                    Gallon.getGallon(rs.getInt("gallon"))
                ));
            }
        } catch(Exception e) {
            Log.error(e.getMessage());
        }
        return agents;
    }

    @Override
    public Agent save(Agent dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO agent (matricule, nom, prenom, mail, id_centre, gallon) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, dto.getMatricule());
            ps.setString(2, dto.getNom());
            ps.setString(3, dto.getPrenom());
            ps.setString(4, dto.getMail());
            ps.setInt(5, dto.getIdCentre());
            ps.setInt(6, dto.getGallon().getID());
            ps.executeUpdate();
        } catch(Exception e) {
            Log.error(e.getMessage());
        }
        return dto;
    }

    @Override
    public Agent update(Agent dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE agent SET nom = ?, prenom = ?, mail = ?, id_centre = ?, gallon = ? WHERE matricule = ?");
            ps.setString(1, dto.getNom());
            ps.setString(2, dto.getPrenom());
            ps.setString(3, dto.getMail());
            ps.setInt(4, dto.getIdCentre());
            ps.setInt(5, dto.getGallon().getID());
            ps.setString(6, dto.getMatricule());
            ps.executeUpdate();
        } catch(Exception e) {
            Log.error(e.getMessage());
        }
        return dto;
    }

    @Override
    public boolean delete(Agent dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM agent WHERE matricule = ?");
            ps.setString(1, dto.getMatricule());
            return ps.executeUpdate() > 0;
        } catch(Exception e) {
            Log.error(e.getMessage());
            return false;
        }
    }
    
}
