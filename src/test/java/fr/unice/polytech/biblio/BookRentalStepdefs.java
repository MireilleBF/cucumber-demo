package fr.unice.polytech.biblio;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Ph. Collet
 */
public class BookRentalStepdefs {

    Bibliotheque biblio = new Bibliotheque();
    Etudiant etudiant;
    Livre livre;

    public BookRentalStepdefs() {} // implementation des steps dans le constructeur (aussi possible dans des méthodes)

    @Given("a student of name {string} and with student id {int}")
    public void givenAStudent(String nomEtudiant, Integer noEtudiant)  // besoin de refactorer int en Integer car utilisation de la généricité par Cucumber Java 8
    {
        etudiant = new Etudiant(biblio);
        etudiant.setNom(nomEtudiant);
        etudiant.setNoEtudiant(noEtudiant);
        biblio.addEtudiant(etudiant);
    }

    @And("a book of title {string}")
    public void andABook(String titreLivre)  {
        Livre liv = new Livre(biblio);
        liv.setTitre(titreLivre);
        biblio.addLivre(liv);
    }


    @Then("There is {int} in his number of rentals")
    public void thenNbRentals(Integer nbEmprunts) {
        assertEquals(nbEmprunts.intValue(),etudiant.getNombreDEmprunt());
    }


    @When("{string} requests his number of rentals")
    public void whenRequestsRentals (String nomEtudiant) {
        etudiant = biblio.getEtudiantByName(nomEtudiant);
    }

    @When("{string} rents the book {string}")
    public void whenRenting(String nomEtudiant, String titreLivre)  {
        etudiant = biblio.getEtudiantByName(nomEtudiant);
        livre = biblio.getLivreByTitle(titreLivre);
        etudiant.emprunte(livre);
    }

    @And("The book {string} is in a rental in the list of rentals")
    public void andNarrowedBook (String titreLivre){
        assertTrue(etudiant.getEmprunt().stream().
                anyMatch(emp -> emp.getLivreEmprunte().getTitre().equals(titreLivre)));
    }

    @And("The book {string} is unavailable")
    public void andUnvailableBook(String titreLivre) {
        assertEquals(true, biblio.getLivreByTitle(titreLivre).getEmprunte());
    }

}
