package fr.unice.polytech.cf.demo.store.purchasing;

import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * To test the history of the customers
 * We need to access to the current customer and the current purchase
 * We choose to use the PurchaseContainer to do it using injection
 * By this way, we can separate the step definitions according to their purpose
 */
public class CustomerHistoryProcess {
    private final PurchaseContainer purchaseContainer;

    public CustomerHistoryProcess(PurchaseContainer purchaseContainer) {
        this.purchaseContainer = purchaseContainer;
    }
    @Then("customer can see that item in purchase history")
    public void customer_can_see_that_item_in_purchase_history() {
        assertTrue(purchaseContainer.customer.purchases().contains(purchaseContainer.purchase));
    }
}
