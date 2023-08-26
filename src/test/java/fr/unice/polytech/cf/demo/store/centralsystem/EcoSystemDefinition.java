package fr.unice.polytech.cf.demo.store.centralsystem;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * To manage the definition of the ecosystem manager in the cucumber tests
 * we need to inject the EcoSystemManager, and we choose to use the FacadeContainer to do it.
 * By cascading the injection, the DAOs are automatically injected in the EcoSystemManager
 * i.e. DAOs are injected in the EcoSystemManager
 */
public class EcoSystemDefinition {

    private EcoSystemManager ecoSystemManager;

    private Exception exception;
    public EcoSystemDefinition(FacadeContainer container) {
        ecoSystemManager = container.ecoSystemManager;
    }

    @When("customer {string} completes the purchases of {int} {string} for {int} dollars in {string}")
    public void customer_completes_the_purchases_of_for_dollars_in(String customerName, Integer number, String kind, Integer price, String storeName) throws UnknownCustomerException, UnknownStoreException {
        try {
            ecoSystemManager.sell(customerName,
                    number + " of " + kind + " in " + storeName,
                    storeName,
                    price
            );
        }
        catch (UnknownCustomerException | UnknownStoreException e ) {
            exception = e;
        }
    }

    @Then("an {string} should be thrown")
    public void an_unknown_exception_should_be_thrown(String exceptionName){
        //TODO : do not use the package name
        String completExceptionName = "fr.unice.polytech.cf.demo.store.centralsystem." + exceptionName;
        assertNotNull(exception);
        try {
            Class exceptionClass = Class.forName(completExceptionName);
            exceptionClass.cast(exception);
        } catch (ClassNotFoundException e) {
            assertTrue(false);
        }
    }
}
