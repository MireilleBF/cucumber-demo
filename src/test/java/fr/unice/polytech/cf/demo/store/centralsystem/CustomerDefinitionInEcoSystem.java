package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.purchasing.Customer;
import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * To test the customer definition in the ecosystem
 * We need to inject the DAO, and we choose to use the FacadeContainer to do it
 */
public class CustomerDefinitionInEcoSystem {

    private final CustomerDAO customerDAO;

    //DON'T FORGET PUBLIC For the constructors !!
    public CustomerDefinitionInEcoSystem(FacadeContainer container) {
        this.customerDAO = container.customerDAO;
    }

    @Given("customer {string} is already registered in ecosystem")
    public void customer_is_already_registered_in_ecosystem(String customer) {
        customerDAO.save(new Customer(customer));
    }

    @Then("customer {string} can see that  {int} {string} in its purchase history")
    public void customer_can_see_that_in_its_purchase_history(String customerName, Integer number, String kind) {
        Customer customer = customerDAO.get(customerName).get();
        assertTrue(//"the purchase history shoud contain the purchase of " + number + " of " +  kind,
                customer.purchases().stream().anyMatch(
                purchase -> purchase.getDescription().contains(kind) && purchase.getDescription().contains(number.toString()) ) ) ;

    }

    @Then("customer {string} cannot see that  {int} {string} in {string} in its purchase history")
    public void customer_cannot_see_that_in_in_its_purchase_history(String customerName, Integer number, String kind, String store) {
        Customer customer = customerDAO.get(customerName).get();
        assertTrue( customer.purchases().stream().noneMatch(
                purchase -> purchase.getDescription().contains(kind) && purchase.getDescription().contains(number.toString()) ) ) ;

    }

}
