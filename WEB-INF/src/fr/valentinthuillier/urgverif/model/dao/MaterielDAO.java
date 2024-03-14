package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.valentinthuillier.urgverif.model.DS;
import fr.valentinthuillier.urgverif.model.dto.Compartiment;
import fr.valentinthuillier.urgverif.model.dto.Materiel;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;
import java.util.HashMap;
import java.util.Map;

public class MaterielDAO implements IDao<Materiel, Integer> {

    public List<Materiel> findByCompartimentAndVehicule(Integer compartimentId, String vehiculeId) {
        List<Materiel> materiels = new ArrayList<>();
        try(Connection con = DS.getConnection()) {

            if(compartimentId == null || vehiculeId == null) {
                throw new IllegalArgumentException("MaterielDAO.findByCompartimentAndVehicule: compartimentId or vehiculeId is null");
            }
            Compartiment compartiment = new CompartimentDAO().findById(compartimentId);
            Vehicule vehicule = new VehiculeDAO().findById(vehiculeId);
            if(compartiment == null || vehicule == null) {
                throw new IllegalArgumentException("MaterielDAO.findByCompartimentAndVehicule: compartiment or vehicule is null");
            }
            PreparedStatement ps = con.prepareStatement("SELECT id, nom, quantite FROM materiel WHERE id_compartiment = ? AND id_vehicule = ?");
            ps.setInt(1, compartiment.getID());
            ps.setString(2, vehicule.getImmatriculation());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Materiel materiel = new Materiel(rs.getInt("id"), rs.getString("nom"), rs.getInt("quantite"), compartiment, vehicule);
                materiels.add(materiel);
            }

        } catch(Exception e) {
            System.out.println("MaterielDAO.findByCompartimentAndVehicule");
            System.out.println(e.getMessage());
        }
        return materiels;
    }

    public Map<Compartiment, List<Materiel>> findByVehicule(Vehicule vehicule) {
        Map<Compartiment, List<Materiel>> materiels = new HashMap<>();
        try(Connection con = DS.getConnection()) {
            if(vehicule == null) {
                throw new IllegalArgumentException("MaterielDAO.findByVehicule: vehicule is null");
            }
            PreparedStatement ps = con.prepareStatement("SELECT id, nom, quantite, id_compartiment FROM materiel WHERE id_vehicule = ?");
            ps.setString(1, vehicule.getImmatriculation());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Compartiment compartiment = new CompartimentDAO().findById(rs.getInt("id_compartiment"));
                Materiel materiel = new Materiel(rs.getInt("id"), rs.getString("nom"), rs.getInt("quantite"), compartiment, vehicule);
                if (!materiels.containsKey(compartiment)) {
                    materiels.put(compartiment, new ArrayList<>());
                }
                materiels.get(compartiment).add(materiel);
            }
        } catch(Exception e) {
            System.out.println("MaterielDAO.findByVehicule");
            System.out.println(e.getMessage());
        }

        return materiels;
    }

    public boolean checkIfIsAlreadyAssignedToVehicule(String immatriculation, Compartiment compartiment, String nomMateriel) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM materiel WHERE id_vehicule = ? AND id_compartiment = ? AND nom = ?");
            ps.setString(1, immatriculation);
            ps.setInt(2, compartiment.getID());
            ps.setString(3, nomMateriel);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch(Exception e) {
            System.out.println("MaterielDAO.checkIfIsAlreadyAssignedToVehicule");
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Materiel findById(Integer id) {
        Materiel materiel = null;
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nom, quantite, id_compartiment, id_vehicule FROM materiel WHERE id = ?");
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Compartiment compartiment = new CompartimentDAO().findById(rs.getInt("id_compartiment"));
                Vehicule vehicule = new VehiculeDAO().findById(rs.getString("id_vehicule"));
                materiel = new Materiel(id, rs.getString("nom"), rs.getInt("quantite"), compartiment, vehicule);
            }
        } catch(Exception e) {
            System.out.println("MaterielDAO.findById");
            System.out.println(e.getMessage());
        }
        return materiel;
    }

    @Override
    public List<Materiel> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Materiel save(Materiel dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO materiel(nom, quantite, id_compartiment, id_vehicule) VALUES(?, ?, ?, ?)");
            ps.setString(1, dto.getNom());
            ps.setInt(2, dto.getQuantite());
            ps.setInt(3, dto.getCompartiment().getID());
            ps.setString(4, dto.getVehicule().getImmatriculation());
            ps.executeUpdate();
            int id = ps.getGeneratedKeys().getInt(1);
            return new Materiel(id, dto.getNom(), dto.getQuantite(), dto.getCompartiment(), dto.getVehicule());

        } catch(Exception e) {
            System.out.println("MaterielDAO.save");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Materiel update(Materiel dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE materiel SET nom = ?, quantite = ?, id_compartiment = ?, id_vehicule = ? WHERE id = ?");
            ps.setString(1, dto.getNom());
            ps.setInt(2, dto.getQuantite());
            ps.setInt(3, dto.getCompartiment().getID());
            ps.setString(4, dto.getVehicule().getImmatriculation());
            ps.setInt(5, dto.getID());
            ps.executeUpdate();
            return dto;
        } catch(Exception e) {
            System.out.println("MaterielDAO.update");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Materiel dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM materiel WHERE id = ?");
            ps.setInt(1, dto.getID());
            ps.executeUpdate();
            return true;
        } catch(Exception e) {
            System.out.println("MaterielDAO.delete");
            System.out.println(e.getMessage());
        }
        return false;
    }
    


}
