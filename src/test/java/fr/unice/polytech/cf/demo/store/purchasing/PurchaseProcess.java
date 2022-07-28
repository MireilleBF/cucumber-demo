package fr.unice.polytech.cf.demo.store.purchasing;

import fr.unice.polytech.cf.demo.store.stocks.CannotRemoveFromStock;
import fr.unice.polytech.cf.demo.store.stores.Store;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class PurchaseProcess {

    private final PurchaseContainer purchaseContainer;

    //The purchaseContainer is automatically injected by Cucumber
    public PurchaseProcess(PurchaseContainer purchaseContainer) {
        this.purchaseContainer = purchaseContainer;
    }
    @Given("customer {string} is already registered")
    public void customer_is_already_registered(String string) {
        //We don't manage the registration of the customer in this part of the demo
        purchaseContainer.customer = new Customer(string);
    }
    @Given("the store {string} contains a stock containing {int} ingredients of name {string}")
    public void the_storeX_contains_a_stock_containing_ingredients_of_name(String storeName, Integer amount, String ingredients) {
        //We don't manage the registration of the stores in this demo
        //We only initialize the stock of the store
        Store store = new Store(storeName);
        store.addIngredientsToStock(ingredients, amount);
        purchaseContainer.store = store;
    }


    @When("customer completes the purchases of {int} {string} of {string} for {int} dollars")
    public void customer_completes_the_purchases_of_of_for_dollars(Integer number, String kind, String ingredient, Integer price) throws CannotRemoveFromStock {
            //We don't deal with the payment itself in this demo
            Purchase item = new Purchase(kind + " of " + ingredient + " in " + purchaseContainer.store.getName(), price);
            purchaseContainer.purchase = item;
            purchaseContainer.store.sell(number, ingredient, price);
            purchaseContainer.customer.purchase(item);
    }


}
