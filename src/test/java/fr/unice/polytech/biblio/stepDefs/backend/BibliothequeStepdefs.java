package fr.unice.polytech.biblio.stepDefs.backend;

import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.BookNotFoundException;
import fr.unice.polytech.biblio.services.StudentRegistry;
import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.entities.Livre;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Etantdonnéque;
import io.cucumber.java.fr.Quand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BibliothequeStepdefs {

    StudentRegistry studentRegistry = new StudentRegistry();
    Bibliotheque bibliotheque;

    @Etantdonné("une bibliothèque")
    public Bibliotheque uneBibliotheque() {
        this.bibliotheque = new Bibliotheque();
        return bibliotheque;
    }

    @Quand("le bibliothécaire  ajoute le livre {string}")
    public void leBibliothecaireAjouteLeLivre(String titreLivre) {
        Livre livre = new Livre(titreLivre);
        bibliotheque.addLivre(livre);
    }

    @Alors("la bibliothèque contient le livre {string} en un exemplaire")
    public void laBibliothequeContientLeLivreEnUnExemplaire(String title) {
        List<Livre> livres = bibliotheque.getLivresByTitle(title);
        assertEquals(1, livres.size());
        Optional<Livre> livre = bibliotheque.getLivreDisponibleByTitle(title);
        assertTrue(livre.isPresent());
        assertEquals(title, livre.get().getTitre());
    }

    @Quand("la scolarité ajoute un étudiant {string} avec le numéro d'étudiant {int}")
    public void laScolariteAjouteUnEtudiantAvecLeNumeroDEtudiant(String nom, int ident) {
        studentRegistry.addStudent(nom, ident);
    }

    @Etantdonnéque("la base ne contient pas d'étudiant {string} avec le numéro d'étudiant {int}")
    public void la_base_ne_contient_pas_d_étudiant_avec_le_numéro_d_étudiant(String string, Integer int1) {
        assertTrue(studentRegistry.findByName(string).isEmpty());
    }

    Etudiant etudiantCourant;

    @Etantdonnéque("la base contient un étudiant {string} avec le numéro d'étudiant {int}")
    public void laBasedoitContenirUnEtudiantAvecLeNumeroDEtudiant(String nom, int ident) {
        studentRegistry.addStudent(nom, ident);
    }

    @Quand("la scolarité cherche un étudiant avec le numéro d'étudiant {int}")
    public void la_scolarité_cherche_un_étudiant_avec_le_numéro_d_étudiant(Integer noEtudiant) {
        etudiantCourant = studentRegistry.findByNumber(noEtudiant).get();
    }

    @Alors("la base contient maintenant un étudiant {string} avec le numéro d'étudiant {int}")
    public void laBaseContientUnEtudiantAvecLeNumeroDEtudiant(String nom, int ident) {
        Etudiant etudiant = studentRegistry.findByName(nom).get();
        assertEquals(nom, etudiant.getName());
        assertEquals(ident, etudiant.getStudentNumber());
    }

    @Alors("la scolarité trouve un étudiant {string} avec le numéro d'étudiant {int}")
    public void la_scolarité_trouve_un_étudiant_avec_le_numéro_d_étudiant(String nom, Integer noEtudiant) {
        assertEquals(nom, etudiantCourant.getName());
        Integer number = etudiantCourant.getStudentNumber();
        assertEquals(noEtudiant, number);
    }

    @Quand("le bibliothécaire  ajoute deux exemplaires du livre {string}")
    public void leBibliothecaireAjouteDeuxExemplairesDuLivre(String titreLivre) {
        Livre livre = new Livre(titreLivre);
        bibliotheque.addLivre(livre);
        livre = new Livre(titreLivre);
        bibliotheque.addLivre(livre);
    }

    @Alors("la bibliothèque contient deux exemplaires du livre {string}")
    public void laBibliothequeContientDeuxExemplairesDuLivre(String titre) {
        List<Livre> livres = bibliotheque.getLivresByTitle(titre);
        assertEquals(2, livres.size());
    }

    Exception exception;

    @Quand("le lecteur cherche le livre avec l'ID U-{int}")
    public void le_lecteur_cherche_le_livre_avec_l_id_u(Integer int1) {
        try {
            bibliotheque.getLivreParBiblioId("U-" + int1);
        } catch (BookNotFoundException e) {
            exception = e;
        }
    }

    @Alors("une exception est levée avec le message {string}")
    public void une_exception_est_levée_avec_le_message(String message) {
        assertEquals(message, exception.getMessage());
    }

    @Etantdonnéque("la bibliothèque contient le livre {string} en {int} exemplaires")
    public void la_bibliothèque_contient_le_livre_en_exemplaires(String titre, Integer nbreExemplaires) {
        for (int i = 0; i < nbreExemplaires; i++) {
            Livre livre = new Livre(titre);
            bibliotheque.addLivre(livre);
        }
    }

    @Quand("le bibliothécaire ajoute les livres suivants:")
    public void le_bibliothécaire_ajoute_les_livres_suivants(List<Map<String, String>> books) {

        for (Map<String, String> book : books) {
            int numberOfBooks = 1;
            if (book.get("exemplaires") != null) {
                numberOfBooks = Integer.parseInt(book.get("exemplaires"));
            }
            // loop to add the number of exemplaires
            for (int i = 0; i < numberOfBooks; i++) {
                Livre livre = new Livre(book.get("titre"));
                livre.setIsbn(book.get("isbn"));
                bibliotheque.addLivre(livre);
            }
        }

    }

    @Alors("la bibliothèque contient au moins les livres suivants:")
    public void la_bibliothèque_contient_au_moins_les_livres_suivants(List<Map<String, String>> books) {
        for (Map<String, String> book : books) {
            List<Livre> livres = bibliotheque.getLivresByTitle(book.get("titre"));
            int numberOfBooks = book.get("exemplaires") == null ? 1 : Integer.parseInt(book.get("exemplaires"));
            assertTrue(livres.size() > numberOfBooks);
        }
    }
}
