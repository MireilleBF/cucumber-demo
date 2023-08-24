package fr.unice.polytech.cf.demo.store.payment;

public interface PaymentSystem {
    void validatePayment(String ident);
    boolean isPaymentValid(String ident);
    String registerPayment(double amount, String customerName);
}
