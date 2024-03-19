package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.valentinthuillier.urgverif.Log;
import fr.valentinthuillier.urgverif.model.DS;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;

public class VehiculeDAO implements IDao<Vehicule, String> {

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

        } catch(Exception e) {
            Log.error("Erreur lors de la récupération du véhicule: " + e.getMessage());
        }
        return vehicule;
    }

    public List<String> findAllTypeEngin() {
        List<String> typeEngin = new ArrayList<>();
        try(Connection con = DS.getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM type_engin");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                typeEngin.add(rs.getString("nom"));
            }

        } catch(Exception e) {
            Log.error("Erreur lors de la récupération des types d'engin: " + e.getMessage());
        }
        return typeEngin;
    }

    @Override
    public List<Vehicule> findAll() {
        Log.warning("Unimplemented method 'findAll' in VehiculeDAO");
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Vehicule save(Vehicule dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO vehicule(immatriculation, type_engin, id_centre) VALUES(?, ?, ?)");
            ps.setString(1, dto.getImmatriculation());
            ps.setString(2, dto.getTypeEngin());
            ps.setInt(3, dto.getCentre().getID());
            ps.executeUpdate();
            return new Vehicule(dto.getImmatriculation(), dto.getTypeEngin(), dto.getCentre());
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

    public void affect(Vehicule v) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO materiel(id_compartiment, id_vehicule, nom, quantite) SELECT id_compartiment, ?, nom, quantite FROM materiel WHERE id_vehicule = ?");
            ps.setString(1, v.getImmatriculation());
            ps.setString(2, v.getTypeEngin().toLowerCase());
            ps.executeUpdate();
        } catch(Exception e) {
            Log.error("Erreur lors de l'affectation du matériel: " + e.getMessage());
        }
    }

}
