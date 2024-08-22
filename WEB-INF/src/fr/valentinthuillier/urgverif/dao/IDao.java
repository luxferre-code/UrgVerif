package fr.valentinthuillier.urgverif.dao;

import java.util.List;

public interface IDao<E extends Object, F extends Object> {

    E find(F id);
    List<E> findAll();
    E save(E entity);
    E update(E entity);
    void delete(F id);
    boolean exists(F id);

}
