package fr.unice.polytech.cf.demo.store.stocks;

import java.util.Objects;

/**
 * Inspired from group e in 20-21
 * Represents an ingredient in the store
 * An ingredient has a name and a price
 * <p>
 * @author Mireille Blay
 * @version %I% %G%
 */
public class Ingredient {
    private String name;
    private int price;

    public Ingredient(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "\"" + name + "\"";
    }

    /**
     * Override of the equals method so that ingredients
     * with the same getDescription and type will be considered equals
     *
     * @param obj an Object to test
     * @return true if objects are equals
     * false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true ;}
        if (!(obj instanceof Ingredient)) { return false;}
        Ingredient ingredient = (Ingredient) obj;
        return ingredient.name.equals(name) && ingredient.price == price;
    }

    /**
     * Needed when overriding and equals method
     *
     * @return the hash of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
