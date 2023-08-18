package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.purchasing.Customer;
import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;
import fr.unice.polytech.cf.demo.store.stores.StoreDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.injectors.AnnotatedFieldInjection;

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
        Optional<Customer> x = customerManager.getCustomer("John");
        assertFalse(x.isPresent());
        customerManager.registerCustomer("John");
        x = customerManager.getCustomer("John");
        assertTrue(x.isPresent());
        customerManager.registerCustomer("Jane");
        x = customerManager.getCustomer("John");
        assertTrue(x.isPresent());
        x = customerManager.getCustomer("Jane");
        assertTrue(x.isPresent());
    }

}