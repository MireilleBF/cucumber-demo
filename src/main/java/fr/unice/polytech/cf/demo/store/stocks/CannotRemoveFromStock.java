package fr.unice.polytech.cf.demo.store.stocks;

public class CannotRemoveFromStock extends Exception {
    private static final long serialVersionUID = 1L;
    public CannotRemoveFromStock(Ingredient ingredient, int amount) {
        super("I can't withdraw from stock" + amount + ingredient.getName());
    }
}
