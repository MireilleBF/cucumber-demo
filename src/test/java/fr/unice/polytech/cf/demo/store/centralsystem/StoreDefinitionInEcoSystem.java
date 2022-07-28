package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.stores.SimpleStore;
import fr.unice.polytech.cf.demo.store.stores.StoreDAO;
import fr.unice.polytech.cf.demo.store.stores.StoreInterface;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreDefinitionInEcoSystem {

    private final StoreDAO storeDAO;
    public StoreDefinitionInEcoSystem(FacadeContainer container) {
        this.storeDAO = container.storeDAO;
    }


    @Given("the store {string} is already registered in ecosystem")
    public void the_store_is_already_registered_in_ecosystem(String store) {
        storeDAO.save(new SimpleStore(store));
    }

    @Then("the revenue of the store {string} is {int} dollars in ecosystem")
    public void the_revenue_of_the_store_is_dollars_in_ecosystem(String storeName, Integer revenue) {
        StoreInterface store = storeDAO.get(storeName).get();
        assertEquals( revenue, store.getRevenue() );
    }

}
