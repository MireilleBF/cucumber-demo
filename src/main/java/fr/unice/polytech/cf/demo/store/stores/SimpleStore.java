package fr.unice.polytech.cf.demo.store.stores;

/**
 * A simple store only has a name and a revenue.
 * The revenue is the sum of all the sales made in the store.
 *
 * @author Mireille Blay
 * @version %I% %G%
 */
public class SimpleStore implements StoreInterface {

    private String name;
    private int revenue;



    public SimpleStore(){
        this("Store");
    }

    public SimpleStore(String name) {
        this.name = name;
    }

    //-----------------------GETTER------------------------------------
    @Override
    public String getName() {
        return name;
    }


    /**
     * increase the revenue of the store with the price of the item
     * @param description the description of the item sold (not used)
     * @param price the price of the item sold
     */
    @Override
    public void sell(String description, int price){
        revenue += price;
    }
    @Override
    public int getRevenue() {
        return revenue;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", revenue=" + revenue +
                '}';
    }
}

