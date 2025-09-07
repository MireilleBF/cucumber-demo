package fr.unice.polytech.biblio.services;

import fr.unice.polytech.biblio.entities.Livre;
import fr.unice.polytech.biblio.entities.Etudiant;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pour des tests simples de la bibliothèque
 * Ph. Collet
 */
class BibliothequeTest {


    //Attention ce test dépend de l'initialisation actuelle de la bibliothèque
    //Il faudra le modifier si on change l'implémentation
    @Test
    void testGetLivres() {
        Bibliotheque biblio = new Bibliotheque();
        List<Livre> livres = biblio.getLivres();
        assertFalse(livres.isEmpty());
        assertEquals(5,livres.size());
    }

    @Test
    void testGetLivreParBiblioId() throws BookNotFoundException {
        Bibliotheque biblio = new Bibliotheque();
        List<Livre> livres = biblio.getLivres();
        livres.stream().forEach(livre -> System.out.println(livre));
        assertFalse(livres.isEmpty());
        assertEquals(5,livres.size());
        Livre livre = biblio.getLivreParBiblioId("U-4");
        assertNotNull(livre);
        livre=biblio.getLivreParBiblioId("J-1");
        livre=biblio.getLivreParBiblioId("U-0");
        assertThrows(BookNotFoundException.class, () -> {
            biblio.getLivreParBiblioId("U-42");
        });
        Livre nlivre = new Livre("Never Let Me Go", 0);
        biblio.addLivre(nlivre);
        System.out.println(livre);
        assertNotNull(biblio.getLivreParBiblioId("NLMG-0"));
    }

    /*
    Scenario: Scolarity has added a student, the student books a book
    Given a registered student named "Paul" with student number 699
    Given a book of title "Never Let Me Go" with id "NLMG-0" has been registered and is available
    When the student with id 699 books the book with id "NLMG-0"
    Then the server should return a success status
    And There is one more loan for the student with the student number 699
    And The book with id "NLMG-0" is no longer available
     */
    @Test
    void testEmprunte( ) throws BookNotFoundException {
        Bibliotheque biblio = new Bibliotheque();
        List<Livre> livres = biblio.getLivres();
        assertFalse(livres.isEmpty());
        assertEquals(5,livres.size());

        StudentRegistry studentRegistry = new StudentRegistry();
        studentRegistry.addStudent("Paul", 699);
        Etudiant etudiant = studentRegistry.findByNumber(699).get();
        assertEquals(0, etudiant.getNombreDEmprunts());

        Livre livre = new Livre("Never Let Me Go", 0);
        biblio.addLivre(livre);
        System.out.println(livre);
        assertNotNull(biblio.getLivreParBiblioId("NLMG-0"));

        assertTrue(biblio.getLivreDisponibleByTitle("Never Let Me Go").isPresent());
        assertTrue(biblio.emprunte(etudiant, livre));
        assertEquals(1, etudiant.getNombreDEmprunts());
        assertFalse(biblio.getLivreDisponibleByTitle("Never Let Me Go").isPresent());

    }

}