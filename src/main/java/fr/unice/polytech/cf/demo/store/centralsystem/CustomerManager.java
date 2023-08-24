package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.purchasing.Customer;
import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;

import java.util.Optional;

public class CustomerManager implements CustomerManagerInterface {
    CustomerDAO customerDAO;
    //Injection using a constructor
    public CustomerManager(CustomerDAO customerDAO){
        this.customerDAO = customerDAO;
    }
    @Override
    public Optional<Customer> findCustomer(String name) {
        return customerDAO.get(name);
    }

    @Override
    public Customer registerCustomer(String customerName) {
        Customer customer = new Customer(customerName);
        customerDAO.save(customer);
        return customer;
    }
}
