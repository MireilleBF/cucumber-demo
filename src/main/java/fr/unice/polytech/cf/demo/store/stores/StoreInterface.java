package fr.unice.polytech.cf.demo.store.stores;


/**
 * Represents a store.
 * <p>
 *     A store is a place where customers can buy items.
 *     A store has a name and a revenue.
 */
public interface StoreInterface extends fr.unice.polytech.cf.demo.store.NamedObject {

    @Override
    String getName();

    int getRevenue();

    void sell(String description, int price);
}
