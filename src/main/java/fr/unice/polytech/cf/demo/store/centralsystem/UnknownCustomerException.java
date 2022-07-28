package fr.unice.polytech.cf.demo.store.centralsystem;

public class UnknownCustomerException extends Exception {
    private static final long serialVersionUID = 1L;
    public UnknownCustomerException(String customerName) {
        super(customerName);
    }
}
