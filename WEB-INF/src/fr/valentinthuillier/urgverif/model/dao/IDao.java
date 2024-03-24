package fr.valentinthuillier.urgverif.model.dao;

import java.util.List;

/**
 * IDaoInterface    -   Cette interface permet de définir les méthodes de base pour les DAO.
 * @author Valentin THUILLIER <valentin.thuillier@luxferre-code.fr>
 * @param <T>   -   Le type de l'objet à manipuler
 * @param <E>   -   Le type de l'identifiant de l'objet
 * @version 1.0
 */
public interface IDao<T, E> {
    
    public T findById(E id);

    public List<T> findAll();

    public T save(T dto);

    public T update(T dto);

    public boolean delete(T dto);

}
