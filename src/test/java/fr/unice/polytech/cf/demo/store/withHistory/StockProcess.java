package fr.unice.polytech.cf.demo.store.withHistory;

import fr.unice.polytech.cf.demo.store.stocks.Stock;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Step definitions for stocks.feature
 * We choose to deal with the stock as a whole
 */
public class StockProcess {
    StockContainer stockContainer;
    boolean accepted;
    Stock currentStock;

    public StockProcess(StockContainer stockContainer) {
        this.stockContainer = stockContainer;
        currentStock = stockContainer.getStock();
    }



    @Given("the stock already contains {int} doses of {string}")
    public void the_stock_already_contains_doses_of_ingredients(Integer number, String ingredient) {
        stockContainer.setStock(new Stock(ingredient, number));
        currentStock = stockContainer.getStock();
    }

    @When("the manager adds {int} doses of {string}")
    public void the_manager_adds_doses_of_ingredients(Integer doses, String ingredient) {
        currentStock.modifyAmount(doses);
    }


    @When("the manager {string} adds {int} doses of {string}")
    public void the_manager_adds_doses_of_chocolates(String name, Integer doses, String ingredient) {
        currentStock.modifyAmount(name,doses);
    }

    @When("the manager {string} removes {int} doses of {string}")
    public void the_manager_removes_doses_of(String name, Integer doses, String ingredient) {
        currentStock.modifyAmount(name,-doses);
    }
    @Then("the stock should contain {int} doses of {string}")
    public void the_stock_should_contain_doses_of_chocolates(Integer number, String ingredient) {
       assertEquals(number, currentStock.getAmount());
    }

    @When("the manager asks to remove  {int} doses of {string}")
    public void the_manager_asks_to_remove_doses_of_chocolates(Integer doses, String ingredient) {
        accepted = currentStock.modifyAmount(-doses);
    }

    @Then("the withdrawal of {string} is refused")
    public void the_withdrawal_is_refused(String ingredient) {
        assertFalse(accepted);
    }



}
