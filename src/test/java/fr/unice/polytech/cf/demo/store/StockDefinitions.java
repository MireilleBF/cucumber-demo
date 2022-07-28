package fr.unice.polytech.cf.demo.store;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class StockDefinitions {

    Stock stock1;
    boolean accepted;
    @Given("the stock already constains {int} doses of chocolates")
    public void the_stock_already_constains_doses_of_chocolates(Integer number) {
        stock1 = new Stock(new Ingredient("chocolate",0), number);
    }

    @When("the manager adds {int} doses of chocolates")
    public void the_manager_adds_doses_of_chocolates(Integer doses) {
        stock1.modifyAmount(doses);
    }

    @Then("the stocks should contain {int} doses of chocolates")
    public void the_stocks_should_contain_doses_of_chocolates(Integer number) {
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
