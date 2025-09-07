package fr.unice.polytech.biblio.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Ph. Collet
 */
class LivreTest { // Just pour vérifier que JUnit 5 est bien configuré

    private Livre livre;

    @BeforeEach
    public void setUp() {
        livre = new Livre("titre");
    }

    @Test
    void testGetTitre() {
        assertEquals("titre", livre.getTitre());
    }

    @Test
    void testConstructorAndEquals() {
        assertEquals("titre", livre.getTitre());
        Livre livre2 = new Livre("titre", 1);
        assertEquals("titre", livre2.getTitre());
        assertNotEquals(livre, livre2);
    }

    @Test
    void testTitreLong() {
        String titre = "Voici une belle histoire";
        Livre livre1 = new Livre(titre);
        assertEquals(titre, livre1.getTitre());
        assertEquals("Vubh-0", livre1.getIdDansBiblio());
        Livre livre2 = new Livre(titre, 1);
        assertEquals(titre, livre2.getTitre());
        assertEquals("Vubh-1", livre2.getIdDansBiblio());
        assertNotEquals(livre1, livre2);
    }
}
