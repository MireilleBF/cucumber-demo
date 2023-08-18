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
    public void save(T t) {
        registerByName.put(t.getName(), t);
    }

    public Optional<T> get(String name) {
        return Optional.ofNullable(registerByName.get(name));
    }

    public void delete(T t) {
        registerByName.remove(t.getName());
    }

    //Update is not implemented

}
