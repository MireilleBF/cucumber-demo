package fr.unice.polytech.cf.demo.store.payment;

import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManager;
import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManagerInterface;

import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;
import fr.unice.polytech.cf.demo.store.purchasing.Purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The only purpose of this class is to show how to use mockito
 * to mock dependencies
 * and to use Picontainer to inject dependencies
 */
 class PaymentTest {

    /**
     * This is not a test, we only use it to show how to use mockito
     */
    @Test
    void validatePaymentTest() {
        PaymentSystem paymentSystem = mock(PaymentSystem.class);
        String ident = "John10";
        when(paymentSystem.registerPayment(10, "John")).thenReturn(ident);
        when(paymentSystem.isPaymentValid(ident)).thenReturn(true);
        String identToPayment = paymentSystem.registerPayment(10, "John");
        boolean isValid = paymentSystem.isPaymentValid(ident);
        assertEquals(ident, identToPayment);
        assertTrue(isValid);
        verify(paymentSystem, times(1)).registerPayment(10, "John");
        verify(paymentSystem, times(1)).isPaymentValid(ident);

    }


    OrderManager orderManager;
    PaymentSystem paymentSystem;
    MutablePicoContainer container;
    @BeforeEach
    void setUp() {
        // We use Picocontainer to inject dependencies in the OrderManager
        // Remark we use Mocks for the dependencies with Payment System
        // and Singleton for the CustomerManager and PaymentSystem
        MutablePicoContainer container = new DefaultPicoContainer(new Caching());
        container.addComponent(CustomerManagerInterface.class, CustomerManager.class);
        container.addComponent(PaymentSystem.class, mock(PaymentSystem.class));
        container.addComponent(OrderManager.class);
        container.addComponent(CustomerDAO.class);
        orderManager = container.getComponent(OrderManager.class);
        paymentSystem = container.getComponent(PaymentSystem.class);
        CustomerManager customerManager = container.getComponent(CustomerManager.class);
        customerManager.registerCustomer("John");
    }


    @Test
    void registerOrderTest() {
        String ident = "John10";

        when(paymentSystem.registerPayment(10, "John")).thenReturn(ident);
        when(paymentSystem.isPaymentValid(ident)).thenReturn(true);

        orderManager.registerOrder(10, "John", new Purchase("Becan", 1000));
        verify( paymentSystem, times(1)).registerPayment(10, "John");
        verify( paymentSystem, times(1)).isPaymentValid(ident);
        OrderManager.Order order = orderManager.getOrder(ident);
        assertNotNull(order);
        assertEquals(ident, order.paymentIdentifier());
    }

    @Test
    void doNotRegisterOrderWhenPaymentNotValidTest() {
        String ident = "John10";
        when(paymentSystem.registerPayment(10, "John")).thenReturn(ident);
        when(paymentSystem.isPaymentValid(ident)).thenReturn(false);

        orderManager.registerOrder(10, "John", new Purchase("Becan", 1000));
        verify( paymentSystem, times(1)).registerPayment(10, "John");
        verify( paymentSystem, times(1)).isPaymentValid(ident);
        OrderManager.Order order = orderManager.getOrder(ident);
        assertNull(order);
    }
}