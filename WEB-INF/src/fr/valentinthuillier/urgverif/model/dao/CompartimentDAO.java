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

/**
 * CompartimentDAOClass -   Cette classe permet de manipuler les compartiments au sein de la base de données.
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @version 1.0
 * @see fr.valentinthuillier.urgverif.Log
 * @see fr.valentinthuillier.urgverif.model.DS
 * @see fr.valentinthuillier.urgverif.model.dto.Compartiment
 * @see fr.valentinthuillier.urgverif.model.dto.Vehicule
 */
public class CompartimentDAO implements IDao<Compartiment, Integer> {

    /**
     * Cette méthode permet de chercher par son identifiant un compartiment.
     * @param   id  (Integer)   -   Son identifiant
     * @return  (Compartiment)  -   L'objet représentant le compartiment ou null si l'identifiant n'est pas enregistré.
     */
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

    /**
     * Cette méthode permet de chercher par son type d'engin tous les compartiments.
     * @param   vehicule    (Vehicule)  -   Le type d'engin
     * @return  (List<Compartiment>)  -   La liste des compartiments ou une liste vide si aucun compartiment n'est enregistré.
     */
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

    /**
     * Cette méthode permet d'obtenir tous les compartiments présent dans la base de données.
     * @return  (List<Compartiment>)  -   La liste des compartiments ou une liste vide si aucun compartiment n'est enregistré.
     */
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

    /**
     * Cette méthode permet d'enregistrer un compartiment dans la base de données.
     * @param   dto (Compartiment)  -   L'objet représentant le compartiment à enregistrer
     * @return  (Compartiment)  -   L'objet représentant le compartiment enregistré ou null si une erreur est survenue.
     */
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

    /**
     * Cette méthode permet de mettre à jour un compartiment dans la base de données.
     * @param   dto (Compartiment)  -   L'objet représentant le compartiment à mettre à jour
     * @return  (Compartiment)  -   L'objet représentant le compartiment mis à jour ou null si une erreur est survenue.
     */
    @Override
    public Compartiment update(Compartiment dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE compartiment SET nom = ?, type_en WHERE id = ?");
            ps.setString(1, dto.getNom());
            ps.setInt(2, dto.getID());
            ps.executeUpdate();
        } catch(Exception e) {
            Log.error("CompartimentDAO.update: Erreur durant la mise à jour du compartiment: " + e.getMessage());
            return null;
        }
        return dto;   
    }

    /**
     * Cette méthode permet de supprimer un compartiment de la base de données.
     * @param   dto (Compartiment)  -   L'objet représentant le compartiment à supprimer
     * @return  (boolean)   -   true si le compartiment a été supprimé, false sinon.
     */
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
