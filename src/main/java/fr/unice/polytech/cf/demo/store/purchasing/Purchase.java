package fr.unice.polytech.cf.demo.store.purchasing;


/**
 * Represents a purchase made by a customer.
 */
public class Purchase {
    private final String description;
    private final int price;

    /**
     * Constructs a new Purchase instance with the given description and price.
     *
     * @param description The description of the purchase.
     * @param price The price of the purchase.
     */
    public Purchase(String description, int price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Purchase : " + description + " " + price;
    }
}
