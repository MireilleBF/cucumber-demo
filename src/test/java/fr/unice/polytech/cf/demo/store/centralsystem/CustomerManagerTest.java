package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.purchasing.Customer;
import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerManagerTest {

    CustomerManagerInterface customerManager;
    @BeforeEach
    void setUp() {
        MutablePicoContainer container = new DefaultPicoContainer();
        container.addComponent(CustomerDAO.class);
        container.addComponent(CustomerManagerInterface.class, CustomerManager.class);
        customerManager = container.getComponent(CustomerManagerInterface.class);
    }

    @Test
    void getCustomerTest() {
        String john = "John";
        Optional<Customer> x = customerManager.findCustomer(john);
        assertFalse(x.isPresent());
        customerManager.registerCustomer(john);
        x = customerManager.findCustomer("John");
        assertTrue(x.isPresent());
        customerManager.registerCustomer("Jane");
        x = customerManager.findCustomer(john);
        assertTrue(x.isPresent());
        x = customerManager.findCustomer("Jane");
        assertTrue(x.isPresent());
    }

}