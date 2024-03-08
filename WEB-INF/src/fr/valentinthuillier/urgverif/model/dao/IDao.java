package fr.valentinthuillier.urgverif.model.dao;

import java.util.List;

public interface IDao<T, E> {
    
    public T findById(E id);

    public List<T> findAll();

    public T save(T dto);

    public T update(T dto);

    public boolean delete(T dto);

}
