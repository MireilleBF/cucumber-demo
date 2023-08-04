package fr.unice.polytech.cf.demo.store.withPicoContainer;

import fr.unice.polytech.cf.demo.store.Stock;

public class StockContainer {
    Stock stock;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock chocolateStock) {
        this.stock = chocolateStock;
    }
}
