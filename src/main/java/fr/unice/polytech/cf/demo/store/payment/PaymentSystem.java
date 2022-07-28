package fr.unice.polytech.cf.demo.store.payment;

public interface PaymentSystem {
    void validatePayment(String ident);
    boolean isPaymentValid(String ident);
    //Return the payment identifier if the payment is valid else return an empty string
    String registerPayment(double amount, String customerName);
}
