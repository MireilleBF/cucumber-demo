package fr.unice.polytech.cf.demo.store.stocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspired from group e in 20-21
 *
 * @author Mireille Blay
 * @version %I% %G%
 */
public class Stock implements StockInterface {

    //Simplified for the demo
    private final List<String> actionHistory = new ArrayList<>();

    private Ingredient ingredient;
    private int amount;
    @Override
    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public int getAmount() {
        return amount;
    }


    public Stock(String ingredient, int amount) {
        this.ingredient = new Ingredient(ingredient,10);
        this.amount = amount;
    }

    public Stock(String ingredient) {
        this(ingredient,0);
    }

    public Stock(Ingredient ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }


    /**
     * modify pAmount to the stock.
     * When pAmount is negative, the amount is retracted.
     *
     * @param   pAmount
     * @return  <code>false</code> if it is a withdrawal and the stock is insufficient
     *          <code>true</code> otherwise.
     */
    @Override
    public boolean modifyAmount(int pAmount) {
        int newAmount = amount + pAmount;
        if (newAmount < 0) {
            return false;
        }
        amount = newAmount;
        return true;
    }

    public void modifyAmount(String managerName, int doses) {
        if (modifyAmount(doses)) {
            actionHistory.add(managerName + " modifies stock : " + doses);
        }
        else {
            actionHistory.add(managerName + " tries to modify stock : " + doses + " but it is refused");
        }
    }

    public String getLastStep() {
        if (actionHistory.isEmpty()) {
            return "Empty actionHistory";
        }
        return actionHistory.get(actionHistory.size() - 1);
    }
}
