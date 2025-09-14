package fr.unice.polytech.biblio.entities;

import fr.unice.polytech.biblio.services.Bibliotheque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EtudiantTest {

    Etudiant e1;
    Etudiant e2;
    Bibliotheque bibliotheque;

    @BeforeEach
    void setUp() {
        e1 = new Etudiant();
        e1.setName("Marcel");
        // Use of underscore to improve readability
        e1.setStudentNumber(123_456);
        e2 = new Etudiant();
        e2.setName("Walid");
        e2.setStudentNumber(6789);
    }

    @Test
    void testGetters() {
        assertEquals("Marcel", e1.getName());
        assertEquals(123_456, e1.getStudentNumber());
        assertEquals("Walid", e2.getName());
        assertEquals(6789, e2.getStudentNumber());
    }

}