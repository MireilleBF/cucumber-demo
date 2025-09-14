package fr.unice.polytech.biblio.services;

import fr.unice.polytech.biblio.entities.Emprunt;
import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.entities.Livre;
import fr.unice.polytech.biblio.services.exceptions.BookAlreadyBorrowedException;
import fr.unice.polytech.biblio.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pour des tests simples de la bibliothèque
 * Ph. Collet
 */
class BibliothequeTest {

    Bibliotheque biblio;

    @BeforeEach
    void setUp() {
        biblio = new Bibliotheque();
    }

    //Attention ce test dépend de l'initialisation actuelle de la bibliothèque
    //Il faudra le modifier si on change l'implémentation
    @Test
    void testGetLivres() {
        List<Livre> livres = biblio.getLivres();
        assertFalse(livres.isEmpty());
        assertEquals(5,livres.size());
    }

    @Test
    void testGetLivreParBiblioId() throws ResourceNotFoundException {
        List<Livre> livres = biblio.getLivres();
        livres.stream().forEach(livre -> System.out.println(livre));
        Livre livre = biblio.getLivreParBiblioId("U-4");
        assertNotNull(livre);
        livre=biblio.getLivreParBiblioId("J-1");
        livre=biblio.getLivreParBiblioId("U-0");
        assertThrows(ResourceNotFoundException.class, () -> {
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
    void testEmprunte( ) throws Exception {
        StudentRegistry studentRegistry = new StudentRegistry();
        studentRegistry.addStudent("Paul", 699);
        Etudiant etudiant = studentRegistry.findByNumber(699).get();
        assertEquals(0, etudiant.getEmprunts(biblio).size());

        Livre livre = new Livre("Never Let Me Go", 0);
        biblio.addLivre(livre);
        System.out.println(livre);
        assertNotNull(biblio.getLivreParBiblioId("NLMG-0"));

        assertTrue(biblio.getLivreDisponibleByTitle("Never Let Me Go").isPresent());
        biblio.emprunte(etudiant, livre);
        Collection<Emprunt> emprunts = etudiant.getEmprunts(biblio);
        assertEquals(1, emprunts.size());
        Emprunt empruntFromStudent = emprunts.iterator().next();
        assertEquals(etudiant, empruntFromStudent.getEmprunteur());
        assertEquals(livre, empruntFromStudent.getLivreEmprunte());
        Emprunt empruntFromStringTitle = etudiant.getEmpruntFor("Never Let Me Go", biblio);
        assertNotNull(empruntFromStringTitle);
        assertEquals(empruntFromStringTitle, empruntFromStudent);
        assertFalse(biblio.getLivreDisponibleByTitle("Never Let Me Go").isPresent());

    }

    @Test
    void testEmprunteDeuxExemplairesDuMemeLivre() throws Exception {
        StudentRegistry studentRegistry = new StudentRegistry();
        studentRegistry.addStudent("Paul", 699);
        Etudiant etudiant = studentRegistry.findByNumber(699).get();

        Livre u0 = biblio.getLivreParBiblioId("U-0");
        Livre u4 = biblio.getLivreParBiblioId("U-4");

        biblio.emprunte(etudiant, u0);
        assertThrows(BookAlreadyBorrowedException.class, () -> {
            biblio.emprunte(etudiant, u0);
        });
        assertThrows(BookAlreadyBorrowedException.class, () -> {
            biblio.emprunte(etudiant, u4);
        });
    }

}