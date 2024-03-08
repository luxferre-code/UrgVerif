package fr.valentinthuillier.urgverif.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            System.out.println(e.getMessage());
        }
        return vehicule;
    }

    @Override
    public List<Vehicule> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Vehicule save(Vehicule dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
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
