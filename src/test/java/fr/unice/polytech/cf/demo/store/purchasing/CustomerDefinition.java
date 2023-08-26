package fr.unice.polytech.cf.demo.store.purchasing;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

/**
 * To test the customer part of the purchasing system
 * Here, we don't deal here with the DAOs,
 * we just test the customer behavior olone
 */
public class CustomerDefinition {

    private Customer customer;


    @When("a customer {string} asks to be created")
    public void a_customer_asks_to_be_created(String name) {
        customer = new Customer(name);
    }

    @Then("there is a customer {string}")
    public void there_is_a_customer(String name) {
        assertNotNull(customer);
        assertEquals(name,customer.getName());
    }
    @Then("the customer {string} has no purchase")
    public void the_customer_has_no_purchase(String string) {
        assertTrue(customer.purchases().isEmpty());
    }

    @Given("A customer {string}")
    public void a_customer(String name) {
        customer = new Customer(name);
    }

    @When("the customer {string} purchases a {string} for ${int}")
    public void the_customer_purchases_a_for_Price(String customer, String kind, Integer price) {
        Purchase purchase = new Purchase(kind, price);
        this.customer.purchase(purchase);
    }
    @Then("the customer {string} has a purchase of {string} for ${int}")
    public void the_customer_has_a_purchase_of_for_Price(String customerName, String kind, Integer price) {
        customer.purchases().stream().filter(purchase -> purchase.getDescription().equals(kind)).forEach(purchase -> assertEquals(price,purchase.getPrice()));
    }

}