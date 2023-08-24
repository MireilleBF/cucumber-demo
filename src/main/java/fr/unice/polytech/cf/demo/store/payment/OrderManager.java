package fr.unice.polytech.cf.demo.store.payment;

import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManager;
import fr.unice.polytech.cf.demo.store.purchasing.Customer;
import fr.unice.polytech.cf.demo.store.purchasing.Purchase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Warning: this class is only a stub for the demo.
 */
public class OrderManager {

    public Order getOrder(String ident) {
        return orders.get(ident);
    }

    public record Order(Customer customer, Purchase p, String paymentIdentifier){
    }

    private Map<String,Order> orders = new ConcurrentHashMap<>();
    private PaymentSystem paymentSystem;
    private CustomerManager customerManager;
    public OrderManager(PaymentSystem paymentSystem, CustomerManager customerManager){
        this.paymentSystem = paymentSystem;
        this.customerManager = customerManager;
    }


    public void registerOrder(double amount, String customerName, Purchase purchase) {
        customerManager.findCustomer(customerName).ifPresent(customer -> {
            String ident = paymentSystem.registerPayment(amount, customerName);
            if (paymentSystem.isPaymentValid(ident)) {
                registerOrder(customer, ident, purchase);
            }
        });
    }

    private void registerOrder(Customer customer, String payment, Purchase purchase) {
        orders.put(payment,new Order(customer,purchase,payment));
    }


}
