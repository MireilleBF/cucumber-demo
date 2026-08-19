package fr.unice.polytech.biblio.stepDefs.backend;

import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.entities.Livre;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.StudentRegistry;
import fr.unice.polytech.biblio.services.exceptions.BookAlreadyBorrowedException;
import fr.unice.polytech.biblio.services.exceptions.ResourceAlreadyExistsException;
import fr.unice.polytech.biblio.services.exceptions.ResourceNotFoundException;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.fr.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ph. Collet
 */
public class EmpruntLivreStepdefs {
    StudentRegistry studentRegistry = new StudentRegistry();
    Bibliotheque biblio = new Bibliotheque();
    Etudiant etudiant;

    @Etantdonné("une bibliothèque avec un etudiant de nom {string} et de noEtudiant {int}")
    public void uneBibliothequeAvecUnEtudiantDeNomEtDeNoEtudiant(String nom, int ident) throws ResourceAlreadyExistsException {
        studentRegistry.updateStudent(ident, nom);
    }

    @Etantdonné("un etudiant de nom {string} et de noEtudiant {int}")
    public void etantDonneUnEtudiant(String nomEtudiant, Integer noEtudiant) throws ResourceAlreadyExistsException
    // besoin de refactorer int en Integer car utilisation de la généricité par Cucumber Java 8
    {
        studentRegistry.updateStudent(noEtudiant, nomEtudiant);
    }

    @Et("un livre de titre {string}")
    public void eUnLivre(String titreLivre) {
        Livre livre = new Livre(titreLivre);
        biblio.addLivre(livre);
    }

    @Et("un livre de titre {string} en deux exemplaires")
    public void unLivreDeTitreEnDeuxExemplaires(String name) {
        addLivre(name,1);
        addLivre(name,2);
    }

    private void addLivre(String titreLivre, int biblioId) {
        Livre livre = new Livre(titreLivre, biblioId);
        biblio.addLivre(livre);
    }


    Exception exception;

    @Quand("{string} emprunte le livre {string}")
    public void quandEmprunte(String nomEtudiant, String titreLivre) {
        etudiant = studentRegistry.findByName(nomEtudiant).get();
        Livre livre = biblio.getLivreDisponibleByTitle(titreLivre).get();
        try {
            biblio.emprunte(etudiant, livre);
        } catch (BookAlreadyBorrowedException e) {
                exception = e;
        }
    }

    @Alors("une exception de type {string} est levée avec le message {string}")
    public void une_exception_est_levée_avec_le_message(String ExceptionClassName, String message) {
        assertEquals(ExceptionClassName, exception.getClass().getSimpleName());
        assertEquals(message, exception.getMessage());
    }

    @Et("Il y a le livre {string} dans un emprunt de la liste d'emprunts")
    public void etLivreDejaEmprunte(String titreLivre) {
        assertTrue(
                etudiant.getEmprunts(biblio).stream().anyMatch(emp -> emp.getLivreEmprunte().getTitre().equals(titreLivre)));
    }

    @Et("Le livre {string} est indisponible")
    public void etLivreDispo(String titreLivre) {
        assertFalse(biblio.getLivreDisponibleByTitle(titreLivre).isPresent());
    }

    @Quand("{string} rend le livre {string}")
    public void rendreLivre(String nomEtudiant, String titreLivre) {
        etudiant = studentRegistry.findByName(nomEtudiant).get();
        Livre livre = etudiant.getEmpruntFor(titreLivre, biblio).getLivreEmprunte();
        biblio.rend(livre);
    }

    @Alors("Le livre {string} est disponible")
    public void leLivreEstDisponible(String titreLivre) {
        assertTrue(biblio.getLivreDisponibleByTitle(titreLivre).isPresent());
    }

    @Alors("Il y a {int} dans son nombre d'emprunts")
    public void ilYADansSonNombreDEmprunts(int nombredEmprunts) {
        assertEquals(nombredEmprunts, etudiant.getEmprunts(biblio).size());
    }

    @Etantdonnéque("{string} a emprunté le livre {string}")
    public void aEmprunteLeLivre(String nomEtudiant, String nomLivre) throws BookAlreadyBorrowedException {
        Etudiant e = etudiant = studentRegistry.findByName(nomEtudiant).get();
        Livre l = biblio.getLivreDisponibleByTitle(nomLivre).get();
        biblio.emprunte(e, l);
    }

    @Quand("{string} emprunte le livre d'id {string}")
    public void emprunteLeLivreDId(String nomEtudiant, String idLivre)
            throws ResourceNotFoundException {
        var etu = studentRegistry.findByName(nomEtudiant).get();
        Livre livre = biblio.getLivreParBiblioId(idLivre);
        try {
            biblio.emprunte(etu, livre);
        } catch (BookAlreadyBorrowedException e) {
            exception = e;
        }
    }

    @Alors("Il y a {int} dans le nombre d'emprunts de {string}")
    public void ilYADansLeNombreDEmpruntsDe(int nombredEmprunts, String nomEtudiant) {
        var etu = studentRegistry.findByName(nomEtudiant).get();
        assertEquals(nombredEmprunts, etu.getEmprunts(biblio).size());
    }

    @Et("Il y a le livre d'id {string} dans les emprunts de {string}")
    public void ilYALeLivreDIdDansLesEmpruntsDe(String idLivre, String nomEtudiant) throws ResourceNotFoundException {
        var etu = studentRegistry.findByName(nomEtudiant).get();
        Livre l = biblio.getLivreParBiblioId(idLivre);
        assertTrue(
                etu.getEmprunts(biblio).stream().anyMatch(emp -> emp.getLivreEmprunte().equals(l)));
    }

    @Et("Le livre d'id {string} est indisponible")
    public void leLivreDIdEstIndisponible(String idLivre) throws ResourceNotFoundException {
        assertTrue(biblio.getLivreParBiblioId(idLivre).estEmprunte());
    }

    @Et("Le livre d'id {string} est disponible")
    public void leLivreDIdEstDisponible(String idLivre) throws ResourceNotFoundException {
        assertFalse(biblio.getLivreParBiblioId(idLivre).estEmprunte());
    }

}
