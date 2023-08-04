package fr.unice.polytech.cf.demo.store.stores;

import fr.unice.polytech.cf.demo.store.stocks.Ingredient;

record Item (Ingredient ingredient, int amount) {
    public int getAmount() {
        return amount;
    }

    public Item(Ingredient ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}
