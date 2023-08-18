package fr.unice.polytech.cf.demo.store.centralsystem;

public class UnknownCustomerException extends Exception {
    public UnknownCustomerException(String customerName) {
        super(customerName);
    }
}
