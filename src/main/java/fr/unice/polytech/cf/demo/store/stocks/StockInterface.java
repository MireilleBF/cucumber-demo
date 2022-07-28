package fr.unice.polytech.cf.demo.store.stocks;

public interface StockInterface {
    Ingredient getIngredient();

    int getAmount();

    boolean modifyAmount(int pAmount);
}
