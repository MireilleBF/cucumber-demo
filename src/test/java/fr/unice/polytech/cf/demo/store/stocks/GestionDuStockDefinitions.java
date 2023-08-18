package fr.unice.polytech.cf.demo.store.stocks;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;

import static org.junit.jupiter.api.Assertions.*;

public class GestionDuStockDefinitions {

    StockInterface stockInterface;
    boolean accepted;
    @Etantdonné("un stock contenant {int} doses de chocolats")
    public void un_stock_contenant_doses_de_chocolats(Integer number) {
        stockInterface = new Stock(new Ingredient("chocolate",0), number);
    }

    @Quand("le gestionnaire ajoute {int} doses de chocolats")
    public void le_gestionnaire_ajoute_doses_de_chocolats(Integer doses) {
        stockInterface.modifyAmount(doses);
    }


    @Alors("le stock  contient {int} doses de chocolats")
    @Alors("le stock contient {int} doses de chocolats")
    public void le_stock_contient_doses_de_chocolats(Integer number) {
        assertEquals(number, stockInterface.getAmount());
    }

    @Quand("le gestionnaire demande à retirer {int} doses of chocolates")
    public void le_gestionnaire_demande_à_retirer_doses_of_chocolates(Integer doses) {
        accepted = stockInterface.modifyAmount(-doses);
    }

    @Alors("le retrait est refusé")
    public void le_retrait_est_refusé() {
        assertFalse(accepted);
    }


    @Alors("le retrait est accepté")
    public void le_retrait_est_accepté() {
        assertTrue(accepted);
    }
}
