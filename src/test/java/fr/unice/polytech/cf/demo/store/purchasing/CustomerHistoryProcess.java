package fr.unice.polytech.cf.demo.store.purchasing;

import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerHistoryProcess {
    private PurchaseContainer purchaseContainer;


    public CustomerHistoryProcess(PurchaseContainer purchaseContainer) {
        this.purchaseContainer = purchaseContainer;
    }
    @Then("customer can see that item in purchase history")
    public void customer_can_see_that_item_in_purchase_history() {
        assertTrue(purchaseContainer.customer.purchases().contains(purchaseContainer.purchase));
    }
}
