package fr.unice.polytech.cf.demo.store;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreDefinitions {

    Store store;
    Ingredient chocolate = new Ingredient("chocolate",0);
    Ingredient strawberry = new Ingredient("strawberry",0);
    Ingredient almond = new Ingredient("almond",0);

    Exception exception = null;

    boolean updated;

    List<Item> items;
    private boolean result;

    @Given("a store of name {string}")
    public void a_store_of_name(String storeName) {
        store = new Store(storeName);
    }

    @Given("an empty store")
    public void an_empty_store() {
        store = new Store("empty");
    }

    @Given("the store contains a stock containing {int} ingredients of name chocolate")
    public void the_store_contains_a_stock_containing_ingredients_of_name_chocolate(Integer chocolateAmount) {
        store.addIngredientsToStock(new Ingredient("chocolate",0), chocolateAmount) ;
    }


    @Given("the store contains a stock containing {int} ingredients of name chocolate and {int} ingredients of name strawberry")
    public void the_store_contains_a_stock_containing_ingredients_of_name_chocolate_and_ingredients_of_name_strawberry(Integer chocolateAmount, Integer strawberryAmount) {
        store.addIngredientsToStock(new Ingredient("chocolate",0), chocolateAmount) ;
        store.addIngredientsToStock(new Ingredient("strawberry",0), strawberryAmount);
    }
    @Given("the store stock contains {int} chocolate topping, {int} strawberry flavour, {int} almond dough")
    public void the_store_stock_contains_chocolate_topping_strawberry_flavour_almond_dough(Integer chocolateAmount, Integer strawberryAmount,Integer almondAmount) {
        the_store_contains_a_stock_containing_ingredients_of_name_chocolate_and_ingredients_of_name_strawberry(chocolateAmount,strawberryAmount);
        store.addIngredientsToStock(new Ingredient("almond",0), almondAmount);
    }

    @When("the manager adds {int} ingredients of name chocolate")
    public void ingredients_of_name_chocolate_are_added_to_the_stock(Integer chocolateAmount) {
    try {
        store.addIngredientsToStock(new Ingredient("chocolate", 0), chocolateAmount);
    } catch (Exception e){
        exception = e;
    }
    }
    @When("user asks the {int} of ingredients of name chocolate")
    public void the_demand_for_ingredients_of_name_chocolate(Integer amount) {
        items = new ArrayList<>();
        items.add((new Item(chocolate,amount)));
        result = store.hasEnoughIngredientsFor(items);
    }

    @Then("there is {int} ingredients of name chocolate  in the stock")
    public void there_is_ingredients_of_name_chocolate_in_the_stock(Integer expectedAmount) {
        assertEquals(expectedAmount,store.getAmountIngredient(chocolate));
    }

    @Then("an error Amount {int} is less than 0 is raised")
    public void the_error_i_cant_withdraw_from_stock_chocolate_is_raised(Integer number) {

        assertNotNull(exception, "error was raised");
        String expectedErrorMessage = "Amount [" + number + "] is less than 0";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }


    @Then("there is {int} ingredients of name strawberry  in the stock")
    public void there_is_ingredients_of_name_strawberry_in_the_stock(Integer expectedAmount) {
        assertEquals(expectedAmount,store.getAmountIngredient(strawberry));
    }



    @Given("a list of items of {int} chocolate, strawberry and almond")
    public void a_list_of_items_of_chocolate_as_strawberry_as_and_almond_as(Integer amount) {
        items = new ArrayList<>();
        items.add(new Item(chocolate,amount));
        items.add(new Item(strawberry,amount));
        items.add(new Item(almond,amount));
    }


    @When("the manager asks store update with this list of items")
    public void update_is_asked_with_this_list_of_items() {
        updated = store.update(items);
    }

    @Then("the stocks have been updated")
    public void the_stocks_have_been_updated() {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(updated);
    }

    @Then("the store contains {int} chocolate topping, {int} strawberry flavour, {int} almond dough")
    public void the_store_contains_chocolate_topping_strawberry_flavour_almond_dough(Integer chocolateDoses, Integer strawberryDoses, Integer almondDoses) {
        assertEquals(chocolateDoses,store.getAmountIngredient(chocolate));
        assertEquals(strawberryDoses,store.getAmountIngredient(strawberry));
        assertEquals(almondDoses,store.getAmountIngredient(almond));
    }




    @Then("the updates return false")
    public void the_updates_return_false() {
        assertFalse(updated);
    }


    @Then("we expect the system to answer true")
    public void we_expect_the_system_to_answer_true() {
        assertTrue(result);
    }

    @Then("we expect the system to answer false")
    public void we_expect_the_system_to_answer_false() {
        assertFalse(result);
    }
}