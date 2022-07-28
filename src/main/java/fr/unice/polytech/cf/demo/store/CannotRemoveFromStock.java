package fr.unice.polytech.cf.demo.store;

public class CannotRemoveFromStock extends Throwable {
    public CannotRemoveFromStock(Ingredient ingredient, int amount) {
        super("I can't withdraw from stock" + amount + ingredient.getName());
    }
}
