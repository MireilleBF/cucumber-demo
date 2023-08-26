package fr.unice.polytech.cf.demo.store.centralsystem;

public class UnknownStoreException extends Exception {
    private static final long serialVersionUID = 1L;
    public UnknownStoreException(String storeName) {
        super(storeName);
    }
}
