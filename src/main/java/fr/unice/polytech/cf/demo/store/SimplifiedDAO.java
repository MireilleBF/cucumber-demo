package fr.unice.polytech.cf.demo.store;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A DAO (data access object (DAO) ) for NamedObjects.
 * @param <T>
 *     The type of the NamedObject
 *     (e.g. StoreInterface, Customer, ...)
 *     that this DAO manages.
 * Later on, we could replace this DAO with a connexion to a database.
 */
public class SimplifiedDAO<T extends NamedObject> {
    //For simplicity's sake, the registerByName Map acts like an in-memory database.
    private final Map<String, T> registerByName = new ConcurrentHashMap<>();

    //CRUD methods

    /**
     * Save a NamedObject in the DAO.
     * @param t
     */
    public void save(T t) {
        registerByName.put(t.getName(), t);
    }

    /**
     * Get a NamedObject from the DAO.
     * @param name
     * @return
     */
    public Optional<T> get(String name) {
        return Optional.ofNullable(registerByName.get(name));
    }

    /**
     * Delete a NamedObject from the DAO.
     * @param t
     */
    public void delete(T t) {
        registerByName.remove(t.getName());
    }

    /**
     * @return  all the NamedObjects from the DAO.
     */
    public List<T> findAll() {
        return new ArrayList<>(registerByName.values());
    }

    //Update is not implemented

}
