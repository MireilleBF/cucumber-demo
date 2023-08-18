package fr.unice.polytech.cf.demo.store.withHistory;

import fr.unice.polytech.cf.demo.store.stocks.Stock;

import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockHistoryProcess {

    private final StockContainer stockContainer;
    public StockHistoryProcess(StockContainer stockContainer) {
        this.stockContainer = stockContainer;
    }
    @Then("the last entry in the history should be {string}")
    public void the_last_entry_in_the_history_should_be(String action) {
         Stock currentStock = stockContainer.getStock();
         assertEquals(action,currentStock.getLastStep());
    }
}
