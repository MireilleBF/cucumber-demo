package fr.unice.polytech.cf.demo.store.payment;

import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManager;
import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManagerInterface;
import fr.unice.polytech.cf.demo.store.purchasing.Customer;
import fr.unice.polytech.cf.demo.store.purchasing.Purchase;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Warning: this class is only a stub for the demo.
 */
public class OrderManager {

    public Order getOrder(String ident) {
        return orders.get(ident);
    }

    public record Order(Customer customer, Purchase p, String paymentIdentifier) {
    }

    private Map<String, Order> orders = new ConcurrentHashMap<>();
    private PaymentSystem paymentSystem;
    private CustomerManagerInterface customerManager;

    public OrderManager(PaymentSystem paymentSystem, CustomerManagerInterface customerManager) {
        this.paymentSystem = paymentSystem;
        this.customerManager = customerManager;
    }


    /**
     * This method creates an order for a customer and a purchase if the payment is valid
     *
     * @param amount
     * @param customerName
     * @param purchase
     * @return the payment identifier associated to the order
     */
    public String registerOrder(double amount, String customerName, Purchase purchase) {
        Optional<Customer> customerOpt = customerManager.findCustomer(customerName);
        String ident = "";
        if (customerOpt.isPresent()) {
            ident = paymentSystem.registerPayment(amount, customerName);
            if (paymentSystem.isPaymentValid(ident)) {
                registerOrder(customerOpt.get(), ident, purchase);
            }
        }
        return ident;
    }

    private void registerOrder(Customer customer, String payment, Purchase purchase) {
        orders.put(payment, new Order(customer, purchase, payment));
    }


}
