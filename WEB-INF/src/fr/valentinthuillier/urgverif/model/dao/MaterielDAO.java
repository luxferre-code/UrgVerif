package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.valentinthuillier.urgverif.Log;
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
                Log.warning("MaterielDAO.findByCompartimentAndVehicule: l'id du compartiment ou du véhicule est null");
                throw new IllegalArgumentException("MaterielDAO.findByCompartimentAndVehicule: l'id du compartiment ou du véhicule est null");
            }
            Compartiment compartiment = new CompartimentDAO().findById(compartimentId);
            Vehicule vehicule = new VehiculeDAO().findById(vehiculeId);
            if(compartiment == null || vehicule == null) {
                Log.warning("MaterielDAO.findByCompartimentAndVehicule: Le compartiment ou le véhicule est null");
                throw new IllegalArgumentException("MaterielDAO.findByCompartimentAndVehicule: Le compartiment ou le véhicule est null");
            }
            PreparedStatement ps = con.prepareStatement("SELECT id, nom, quantite, valide FROM materiel WHERE id_compartiment = ? AND id_vehicule = ?");
            ps.setInt(1, compartiment.getID());
            ps.setString(2, vehicule.getImmatriculation());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Materiel materiel = new Materiel(rs.getInt("id"), rs.getString("nom"), rs.getInt("quantite"), compartiment, vehicule, rs.getBoolean("valide"));
                materiels.add(materiel);
            }

        } catch(Exception e) {
            Log.error("MaterielDAO.findByCompartimentAndVehicule: Erreur lors de la récupération des matériels: " + e.getMessage());
        }
        return materiels;
    }

    public Map<Compartiment, List<Materiel>> findByVehicule(Vehicule vehicule) {
        Map<Compartiment, List<Materiel>> materiels = new HashMap<>();
        try(Connection con = DS.getConnection()) {
            if(vehicule == null) {
                Log.warning("MaterielDAO.findByVehicule: Le véhicule est null");
                throw new IllegalArgumentException("MaterielDAO.findByVehicule: vehicule is null");
            }
            PreparedStatement ps = con.prepareStatement("SELECT id, nom, quantite, id_compartiment, valide FROM materiel WHERE id_vehicule = ?");
            ps.setString(1, vehicule.getImmatriculation());
            ResultSet rs = ps.executeQuery();

            Map<Integer, Compartiment> ids = new HashMap<>();
            for(Compartiment compartiment : new CompartimentDAO().findAllByVehicule(vehicule)) {
                materiels.put(compartiment, new ArrayList<>());
                ids.put(compartiment.getID(), compartiment);
            }

            while(rs.next()) {
                Materiel materiel = new Materiel(rs.getInt("id"), rs.getString("nom"), rs.getInt("quantite"), ids.get(rs.getInt("id_compartiment")), vehicule, rs.getBoolean("valide"));
                materiels.get(ids.get(rs.getInt("id_compartiment"))).add(materiel);
            }

            // Enleve les compartiments sans materiels
            List<Compartiment> toRm = new ArrayList<>();
            for(Compartiment compartiment : materiels.keySet()) {
                if(materiels.get(compartiment).isEmpty()) {
                    toRm.add(compartiment);
                }
            }
            for(Compartiment compartiment : toRm) {
                materiels.remove(compartiment);
            }

        } catch(Exception e) {
            Log.error("MaterielDAO.findByVehicule: Erreur lors de la récupération des matériels: " + e.getMessage());
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
            Log.error("MaterielDAO.checkIfIsAlreadyAssignedToVehicule: Erreur lors de la vérification de l'affectation du matériel: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Materiel findById(Integer id) {
        Materiel materiel = null;
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT nom, quantite, id_compartiment, id_vehicule, valide FROM materiel WHERE id = ?");
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Compartiment compartiment = new CompartimentDAO().findById(rs.getInt("id_compartiment"));
                Vehicule vehicule = new VehiculeDAO().findById(rs.getString("id_vehicule"));
                materiel = new Materiel(id, rs.getString("nom"), rs.getInt("quantite"), compartiment, vehicule, rs.getBoolean("valide"));
            }
        } catch(Exception e) {
            Log.error("MaterielDAO.findById: Erreur lors de la récupération du matériel: " + e.getMessage());
        }
        return materiel;
    }

    @Override
    public List<Materiel> findAll() {
        Log.warning("Unimplemented method 'findAll' in MaterielDAO");
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Materiel save(Materiel dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO materiel(nom, quantite, id_compartiment, id_vehicule, valide) VALUES(?, ?, ?, ?, ?)");
            ps.setString(1, dto.getNom());
            ps.setInt(2, dto.getQuantite());
            ps.setInt(3, dto.getCompartiment().getID());
            ps.setString(4, dto.getVehicule().getImmatriculation());
            ps.setBoolean(5, dto.getValide());
            ps.executeUpdate();
            int id = ps.getGeneratedKeys().getInt(1);
            return new Materiel(id, dto.getNom(), dto.getQuantite(), dto.getCompartiment(), dto.getVehicule(), dto.getValide());

        } catch(Exception e) {
            Log.error("MaterielDAO.save: Erreur lors de l'ajout du matériel: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Materiel update(Materiel dto) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE materiel SET nom = ?, quantite = ?, id_compartiment = ?, id_vehicule = ?, valide = ? WHERE id = ?");
            ps.setString(1, dto.getNom());
            ps.setInt(2, dto.getQuantite());
            ps.setInt(3, dto.getCompartiment().getID());
            ps.setString(4, dto.getVehicule().getImmatriculation());
            ps.setBoolean(5, dto.getValide());
            ps.setInt(6, dto.getID());
            ps.executeUpdate();
            return dto;
        } catch(Exception e) {
            Log.error("MaterielDAO.update: Erreur lors de la mise à jour du matériel: " + e.getMessage());
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
            Log.error("MaterielDAO.delete: Erreur lors de la suppression du matériel: " + e.getMessage());
        }
        return false;
    }
    


}
