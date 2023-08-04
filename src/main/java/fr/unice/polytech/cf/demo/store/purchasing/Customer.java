package fr.unice.polytech.cf.demo.store.purchasing;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    List<Purchase> history = new ArrayList<>();

    public List<Purchase> purchases() {
        return history;
    }
    String name;
     public String getName() {
        return name;
    }
    public Customer(String name) {
        this.name = name;
    }

    public void purchase(Purchase purchase) {
        history.add(purchase);
    }

    public Purchase getPurchase(String description) {
        return history.stream().filter(purchase -> purchase.getDescription().equals(description)).findFirst().orElse(null);
    }

}