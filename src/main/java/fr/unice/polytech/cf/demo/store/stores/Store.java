package fr.unice.polytech.cf.demo.store.stores;

import fr.unice.polytech.cf.demo.store.stocks.CannotRemoveFromStock;
import fr.unice.polytech.cf.demo.store.stocks.Ingredient;
import fr.unice.polytech.cf.demo.store.stocks.Stock;


import java.time.LocalTime;
import java.util.*;

/**
 * Inspired from group e in 20-21
 *
 * @author Mireille Blay
 * @version %I% %G%
 */
public class Store {

    private String name;
    private LocalTime openingTime;
    private LocalTime closeTime;

    private int revenue;
    Map<Ingredient, Stock> stocks = new HashMap<>();

    private Ingredient findIngredient(String name) {
        if (stocks.keySet().isEmpty()) return new Ingredient(name, 0);
        return stocks.keySet().stream().filter(ingredient -> ingredient.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Simple store constructor
     *
     * @param name         the store's getDescription
     * @param pOpeningTime store opening time
     * @param pCloseTime   store closing time
     */
    public Store(String name, LocalTime pOpeningTime, LocalTime pCloseTime) {
        this.name = name;
        this.stocks = new HashMap<>();
        this.openingTime = pOpeningTime;
        this.closeTime = pCloseTime;
    }

    public Store(String name) {
        this(name, LocalTime.of(8, 00), LocalTime.of(20, 00));
    }


    //-----------------------GETTER------------------------------------
    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public String getName() {
        return name;
    }


    //-----------------------SETTER------------------------------------
    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    //-----------------------Manage stocks------------------------------------

    /**
     * Search and return the stock of the store containing the wanted ingredient
     *
     * @param ingredient the wanted ingredient
     * @return the stock containing the ingredient
     */
    Optional<Stock> getStockByIngredient(Ingredient ingredient) {
        return Optional.ofNullable(stocks.get(ingredient));
    }


    public int getAmountIngredient(Ingredient ingredient) {
        Optional<Stock> stockWithIngredient = getStockByIngredient(ingredient);
        if (stockWithIngredient.isPresent()) {
            return stockWithIngredient.get().getAmount();
        } else return 0;
    }


    /**
     * changes the amount of a stock
     *
     * @param ingredient
     * @param amount
     * @return <code>true</code> if the amount has been modified
     * <code>false</code> otherwise
     **/
    private boolean changeAmount(Ingredient ingredient, int amount) {
        Optional<Stock> stockWithIngredient = getStockByIngredient(ingredient);
        if (stockWithIngredient.isPresent()) {
            return stockWithIngredient.get().modifyAmount(amount);
        } else return false;
    }


    /**
     * Add an amount of Ingredients to a stock.
     * If the Ingredient does not exist, create a stock containing it.
     *
     * @param ingredient the targeted ingredient
     * @param amount     the quantity to add
     */
    public void addIngredientsToStock(Ingredient ingredient, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount [" + amount + "] is less than 0");
        }
        boolean added = changeAmount(ingredient, amount);
        if (!added) {//create the stock
            stocks.put(ingredient, new Stock(ingredient, amount));
        }
    }

    public void addIngredientsToStock(String ingredientName, int amount) {
        Ingredient ingredient = this.findIngredient(ingredientName);
       addIngredientsToStock(ingredient, amount);
    }

    /**
     * Remove an amount of Ingredients from a stock.
     * if the stock doesn't exist, do nothing
     *
     * @param ingredient the targeted ingredient
     * @param amount     the quantity to remove
     **/
    public void removeIngredientsFromStock(Ingredient ingredient, int amount) throws CannotRemoveFromStock {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount [" + amount + "] is less or equal to 0");
        }
        if (!changeAmount(ingredient, -amount))
            throw new CannotRemoveFromStock(ingredient, amount);
    }


    /**
     * Tells whether or not the store can take care of THE GIVEN list of Items
     *
     * @return false in case of shortages
     **/
    public boolean canTakeCareOf(List<Item> items) {
        if (items == null) {
            throw new IllegalArgumentException("items must not be  null");
        }
        //check for ingredient shortage
        return hasEnoughIngredientsFor(items);
    }

    /**
     * Count the quantity of each ingredient and check if the
     * stocks provide enough ingredients.
     *
     * @param pItems the list of <code>Purchase</code>to check
     * @return true if the stocks provide enough ingredients
     * false if not
     */
    boolean hasEnoughIngredientsFor(List<Item> pItems) {
        for (Item item : pItems) {
            if (getAmountIngredient(item.getIngredient()) < item.getAmount())
                return false;
        }
        return true;
    }

    public boolean update(List<Item> pItems) throws CannotRemoveFromStock {
        if (!hasEnoughIngredientsFor(pItems)) {
            return false;
        }
        for (Item item : pItems) {
                removeIngredientsFromStock(item.getIngredient(), item.getAmount());
        }
        return true;
    }

    public void sell(Integer number, String ingredient, Integer price) throws CannotRemoveFromStock {
        removeIngredientsFromStock(findIngredient(ingredient), number);
        revenue += price;
    }

    public Optional<Stock> getStock(String ingredient) {
        return getStockByIngredient(findIngredient(ingredient));
    }

    public int getRevenue() {
        return revenue;
    }
}

