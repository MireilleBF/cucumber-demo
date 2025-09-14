package fr.unice.polytech.biblio.stepDefs.backend;

import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.entities.Livre;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.StudentRegistry;
import fr.unice.polytech.biblio.services.exceptions.BookAlreadyBorrowedException;
import fr.unice.polytech.biblio.services.exceptions.ResourceAlreadyExistsException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ph. Collet
 *
 */
public class BookRentalStepdefs {

    StudentRegistry studentRegistry = new StudentRegistry();
    Bibliotheque biblio = new Bibliotheque();
    Etudiant etudiant;
    Livre livre;

    public BookRentalStepdefs() {
    } // implementation des steps dans le constructeur (aussi possible dans des
      // méthodes)

    @Given("a student of name {string} and with student id {int}")
    public void givenAStudent(String nomEtudiant, Integer noEtudiant) throws ResourceAlreadyExistsException
    // besoin de refactorer int en Integer car utilisation de la généricité par Cucumber Java 8
    {
        studentRegistry.updateStudent(noEtudiant, nomEtudiant);
        etudiant = studentRegistry.findByNumber(noEtudiant).orElse(null);
    }

    @And("a book of title {string}")
    public void andABook(String titreLivre) {
        Livre liv = new Livre(titreLivre);
        biblio.addLivre(liv);
    }

    @Then("There is {int} in his number of rentals")
    public void thenNbRentals(Integer nbEmprunts) {
        assertEquals(nbEmprunts, etudiant.getEmprunts(biblio).size());
    }

    @When("{string} requests his number of rentals")
    public void whenRequestsRentals(String nomEtudiant) {
        etudiant = studentRegistry.findByName(nomEtudiant).orElse(null);
    }

    @When("{string} rents the book {string}")
    public void whenRenting(String nomEtudiant, String titreLivre) throws BookAlreadyBorrowedException {
        etudiant = studentRegistry.findByName(nomEtudiant).get();
        if (biblio.getLivreDisponibleByTitle(titreLivre).isPresent()) {
            livre = biblio.getLivreDisponibleByTitle(titreLivre).get();
            biblio.emprunte(etudiant, livre);
        }
    }

    @And("The book {string} is in a rental in the list of rentals")
    public void andNarrowedBook(String titreLivre) {
        assertTrue(
                etudiant.getEmprunts(biblio).stream().anyMatch(emp -> emp.getLivreEmprunte().getTitre().equals(titreLivre)));
    }

    @And("The book {string} is unavailable")
    public void andUnvailableBook(String titreLivre) {
        assertTrue(biblio.getLivreDisponibleByTitle(titreLivre).isEmpty());
    }

    @Given("{string} has rent the book {string}")
    public void hasRentTheBook(String studentName, String bookTitle) throws BookAlreadyBorrowedException {
        Etudiant e = studentRegistry.findByName(studentName).orElse(null);
        Livre l = biblio.getLivreDisponibleByTitle(bookTitle).get();
        biblio.emprunte(e, l);
    }

    @When("{string} returns the book {string}")
    public void returnsTheBook(String studentName, String bookTitle) {
        Etudiant e = studentRegistry.findByName(studentName).orElse(null);
        Livre l = e.getEmpruntFor(bookTitle, biblio).getLivreEmprunte();
        biblio.rend(l);
    }

    @And("The book {string} is available")
    public void theBookIsAvailable(String title) {
        assertTrue(biblio.getLivreDisponibleByTitle(title).isPresent());
    }
}
