package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.purchasing.Customer;
import fr.unice.polytech.cf.demo.store.stores.StoreInterface;

import java.util.Optional;

public interface CustomerManagerInterface {
    Optional<Customer> getCustomer(String name);

    Customer registerCustomer(String customerName) ;
}

