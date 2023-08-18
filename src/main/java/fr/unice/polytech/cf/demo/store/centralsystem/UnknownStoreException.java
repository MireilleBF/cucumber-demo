package fr.unice.polytech.cf.demo.store.centralsystem;

public class UnknownStoreException extends Exception {
    public UnknownStoreException(String storeName) {
        super(storeName);
    }
}
