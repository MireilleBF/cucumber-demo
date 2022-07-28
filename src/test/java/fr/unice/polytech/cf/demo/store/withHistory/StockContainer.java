package fr.unice.polytech.cf.demo.store.withHistory;

import fr.unice.polytech.cf.demo.store.stocks.Stock;

public class StockContainer {
    Stock stock;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock chocolateStock) {
        this.stock = chocolateStock;
    }
}
