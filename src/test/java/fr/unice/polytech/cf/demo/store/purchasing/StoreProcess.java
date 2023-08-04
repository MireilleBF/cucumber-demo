package fr.unice.polytech.cf.demo.store.purchasing;

import fr.unice.polytech.cf.demo.store.stocks.Stock;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreProcess {

    private PurchaseContainer purchaseContainer;

    public StoreProcess(PurchaseContainer purchaseContainer) {
        this.purchaseContainer = purchaseContainer;
    }

    @Then("the revenue of the store {string} is {int} dollars")
    public void the_revenue_of_the_store_is_dollars(String storeName, Integer revenue) {
        assertEquals(revenue, purchaseContainer.store.getRevenue());
        assertEquals(storeName, purchaseContainer.store.getName());
    }

    @Then("there is {int} ingredients of name {string}  in the stock")
    public void there_is_ingredients_of_name_in_the_stock(Integer amount, String ingredient) {
        Stock stock = purchaseContainer.store.getStock(ingredient).orElse(new Stock(ingredient));
        assertEquals(amount,stock.getAmount());
    }
}
