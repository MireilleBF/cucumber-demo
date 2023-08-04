package fr.unice.polytech.cf.demo.store;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspired from group e in 20-21
 *
 * @author Mireille Blay
 * @version %I% %G%
 */
public class Stock {

    private List<String> history = new ArrayList<String>();

    private Ingredient ingredient;

    public int getAmount() {
        return amount;
    }

    private int amount;

    public Stock(Ingredient ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * modify pAmount to the stock.
     * When pAmount is negative, the amount is retracted.
     *
     * @param   pAmount
     * @return  <code>false</code> if it is a withdrawal and the stock is insufficient
     *          <code>true</code> otherwise.
     */
    public boolean modifyAmount(int pAmount) {
        int newAmount = amount + pAmount;
        if (newAmount < 0) {
            return false;
        }
        amount = newAmount;
        return true;
    }

    public void modifyAmount(String name, int doses) {
        if (modifyAmount(doses))
            history.add(name + " modifies stock : " + doses);
        else
            history.add(name + " tries to modify stock : " + doses + " but it is refused");
    }

    public String getLastStep() {
        if (history.isEmpty()) {
            return "Empty history";
        }
        return history.get(history.size() - 1);
    }
}
