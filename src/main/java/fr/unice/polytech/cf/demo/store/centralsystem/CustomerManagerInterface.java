package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.purchasing.Customer;

import java.util.Optional;

public interface CustomerManagerInterface {
    Optional<Customer> findCustomer(String name);

    Customer registerCustomer(String customerName) ;
}

