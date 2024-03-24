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

/**
 * MaterielDAOClass -   Cette classe permet de manipuler le matériel au sein de la base de données.
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @version 1.0
 * @see fr.valentinthuillier.urgverif.Log
 * @see fr.valentinthuillier.urgverif.model.DS
 * @see fr.valentinthuillier.urgverif.model.dto.Materiel
 * @see fr.valentinthuillier.urgverif.model.dto.Compartiment
 * @see fr.valentinthuillier.urgverif.model.dto.Vehicule
 */
public class MaterielDAO implements IDao<Materiel, Integer> {

    /**
     * Cette méthode permet de charger une liste de matériels en fonction de l'id du compartiment et de l'immatriculation du véhicule.
     * @param   compartimentId  (Integer)   -   L'id du compartiment
     * @param   immatriculation  (String)   -   L'immatriculation du véhicule
     * @return  (List<Materiel>)  -   La liste des matériels
     */
    public List<Materiel> findByCompartimentAndVehicule(Integer compartimentId, String immatriculation) {
        List<Materiel> materiels = new ArrayList<>();
        try(Connection con = DS.getConnection()) {

            if(compartimentId == null || immatriculation == null) {
                Log.warning("MaterielDAO.findByCompartimentAndVehicule: l'id du compartiment ou du véhicule est null");
                throw new IllegalArgumentException("MaterielDAO.findByCompartimentAndVehicule: l'id du compartiment ou du véhicule est null");
            }
            Compartiment compartiment = new CompartimentDAO().findById(compartimentId);
            Vehicule vehicule = new VehiculeDAO().findById(immatriculation);
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

    /**
     * Cette méthode permet de charger une map de matériels en fonction de l'immatriculation du véhicule, dont la clé est le compartiment dans lequel il appartient.
     * @param   vehicule  (Vehicule)   -   Le véhicule
     * @return  (Map<Compartiment, List<Materiel>>)  -   La map des matériels
     */
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
            for(Map.Entry<Compartiment, List<Materiel>> entry : materiels.entrySet()) {
                if(entry.getValue().isEmpty()) {
                    toRm.add(entry.getKey());
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

    /**
     * Cette méthode permet de vérifier si le matériel est déjà affecté à un véhicule.
     * @param immatriculation   (String)    -   L'immatriculation du véhicule
     * @param compartiment  (Compartiment)  -   Le compartiment
     * @param nomMateriel   (String)    -   Le nom du matériel
     * @return  (boolean)   -   Vrai si le matériel est déjà affecté, faux sinon
     */
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

    /**
     * Cette méthode permet de charger un matériel en fonction de son id.
     * @param   id  (Integer)   -   L'id du matériel
     * @return  (Materiel)  -   Le matériel, ou null si non trouvé
     */
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

    /**
     * Cette méthode permet d'enregistrer un matériel dans la base de données.
     * @param   dto (Materiel)  -   L'objet représentant le matériel à enregistrer
     * @return  (Materiel)  -   Le matériel enregistré, ou null en cas d'erreur
     */
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

    /**
     * Cette méthode permet de mettre à jour un matériel dans la base de données (utilisons l'id indiqué dans l'objet).
     * @param   dto (Materiel)  -   L'objet représentant le matériel à mettre à jour
     * @return  (Materiel)  -   Le matériel mis à jour, ou null en cas d'erreur
     */
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

    /**
     * Cette méthode permet de mettre à jour une liste de matériels dans la base de données.
     * @param   updates (List<Materiel>)  -   La liste des matériels à mettre à jour
     * @return  (boolean)   -   Vrai si la mise à jour a réussi, faux sinon
     */
    public boolean updateAll(List<Materiel> updates) {
        try(Connection con = DS.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE materiel SET nom = ?, quantite = ?, id_compartiment = ?, id_vehicule = ?, valide = ? WHERE id = ?");
            for(Materiel dto : updates) {
                ps.setString(1, dto.getNom());
                ps.setInt(2, dto.getQuantite());
                ps.setInt(3, dto.getCompartiment().getID());
                ps.setString(4, dto.getVehicule().getImmatriculation());
                ps.setBoolean(5, dto.getValide());
                ps.setInt(6, dto.getID());
                ps.addBatch();
            }
            return ps.executeBatch().length == updates.size();
        } catch(Exception e) {
            Log.error("MaterielDAO.updateAll: Erreur lors de la mise à jour des matériels: " + e.getMessage());
        }
        return false;
    }

    /**
     * Cette méthode permet de supprimer un matériel de la base de données.
     * @param   dto (Materiel)  -   L'objet représentant le matériel à supprimer
     * @return  (boolean)   -   Vrai si la suppression a réussi, faux sinon
     */
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
