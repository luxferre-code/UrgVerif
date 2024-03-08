package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("VehiculeDAO.findById");
            System.out.println(e.getMessage());
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
            System.out.println("VehiculeDAO.findAllTypeEngin");
            System.out.println(e.getMessage());
        }
        return typeEngin;
    }

    @Override
    public List<Vehicule> findAll() {
        // TODO Auto-generated method stub
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
            System.out.println("VehiculeDAO.save");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Vehicule update(Vehicule dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(Vehicule dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    


}
