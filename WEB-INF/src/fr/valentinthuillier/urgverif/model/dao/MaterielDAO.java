package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fr.valentinthuillier.jeq.database.DS;
import fr.valentinthuillier.urgverif.model.dto.Compartiment;
import fr.valentinthuillier.urgverif.model.dto.Materiel;
import fr.valentinthuillier.urgverif.model.dto.Vehicule;

public class MaterielDAO implements IDao<Materiel, Object[]> {

    /**
     * Important : ids[0] => Integer | ids[1] => String
     */
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
            PreparedStatement ps = con.prepareStatement("SELECT id, nom, quantite FROM materiel WHERE id_compartiment = ? AND immatriculation_vehicule = ?");
            ps.setInt(1, compartiment.getID());
            ps.setString(2, vehicule.getImmatriculation());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Materiel materiel = new Materiel(rs.getInt("id"), rs.getString("nom"), rs.getInt("quantite"), compartiment, vehicule);
                materiels.add(materiel);
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return materiels;
    }

    public List<Materiel> findByVehicule(Vehicule vehicule) {
        List<Materiel> materiels = new ArrayList<>();
        try(Connection con = DS.getConnection()) {
            if(vehicule == null) {
                throw new IllegalArgumentException("MaterielDAO.findByVehicule: vehicule is null");
            }
            PreparedStatement ps = con.prepareStatement("SELECT id, nom, quantite, id_compartiment FROM materiel WHERE immatriculation_vehicule = ?");
            ps.setString(1, vehicule.getImmatriculation());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Compartiment compartiment = new CompartimentDAO().findById(rs.getInt("id_compartiment"));
                Materiel materiel = new Materiel(rs.getInt("id"), rs.getString("nom"), rs.getInt("quantite"), compartiment, vehicule);
                materiels.add(materiel);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return materiels;
    }

    @Override
    public Materiel findById(Object[] id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Materiel> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Materiel save(Materiel dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Materiel update(Materiel dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(Materiel dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    


}
