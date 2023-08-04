package fr.unice.polytech.cf.demo.store.purchasing;


public class Purchase {
    String description;
    int price;

    public Purchase(String itemtype, int currentPrice) {
        description = itemtype;
        price = currentPrice;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
