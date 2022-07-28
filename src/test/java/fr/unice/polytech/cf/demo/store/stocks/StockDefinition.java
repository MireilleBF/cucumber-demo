package fr.unice.polytech.cf.demo.store.stocks;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step definitions for stocks.feature
 * We choose to deal with the stock as a whole
 */
public class StockDefinition {
    Stock stock1;


    boolean accepted;

    @Given("the stock already contains {int} doses of chocolates")
    public void the_stock_already_contains_doses_of_chocolates(Integer number) {
        stock1 = new Stock(new Ingredient("chocolate",0), number);
    }

    @When("the manager adds {int} doses of chocolates")
    public void the_manager_adds_doses_of_chocolates(Integer doses) {
        stock1.modifyAmount(doses);
    }


    @When("the manager {string} adds {int} doses of chocolates")
    public void the_manager_adds_doses_of_chocolates(String name, Integer doses) {
        stock1.modifyAmount(name,doses);
    }

    @Then("the stock should contain {int} doses of chocolates")
    public void the_stock_should_contain_doses_of_chocolates(Integer number) {
       assertEquals(number, stock1.getAmount());
    }

    @When("the manager asks to remove  {int} doses of chocolates")
    public void the_manager_asks_to_remove_doses_of_chocolates(Integer doses) {
        accepted = stock1.modifyAmount(-doses);
    }

    @Then("the withdrawal is refused")
    public void the_withdrawal_is_refused() {
        assertFalse(accepted);
    }


}
