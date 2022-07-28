package fr.unice.polytech.cf.demo.store.purchasing;

import fr.unice.polytech.cf.demo.store.NamedObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A Customer has a name and a history of purchases.
 */
public class Customer implements NamedObject {

    //Store the history of purchases
    private final List<Purchase> history = new ArrayList<>();
    private final String name;


    /**
     * @return the history of purchases
     */
    public List<Purchase> purchases() {
        return history;
    }

     @Override
     public String getName() {
        return name;
    }

    /**
     * Create a new Customer with a name.
     * @param name
     */
    public Customer(String name) {
        this.name = name;
    }

    public void purchase(Purchase purchase) {
        history.add(purchase);
    }

    /**
     * @param description
     * @return the purchase with the given description if it exists, null otherwise.
     */
    public Purchase getPurchase(String description) {
        return history.stream().filter(purchase -> purchase.getDescription().equals(description)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "Customer{" + "name='" + name + '\'' + ", history=" + history + '}';
    }
}