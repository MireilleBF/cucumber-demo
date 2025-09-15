package fr.unice.polytech.biblio.services;

import fr.unice.polytech.biblio.entities.Emprunt;
import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.entities.Livre;
import fr.unice.polytech.biblio.repositories.BookRepository;
import fr.unice.polytech.biblio.services.exceptions.BookAlreadyBorrowedException;
import fr.unice.polytech.biblio.services.exceptions.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.*;

/**
 * Ph. Collet
 * modifié par M. Blay-Fornarino
 *
 */
public class Bibliotheque {

	public static final int DUREE_MAX_EMPRUNT = 15;

	// Nous séparons les étudiants et les livres
	// La bibliothèque ne connait que les livres mais interagit avec les étudiants
	// pour les emprunts

    //BookRepository gère la persistance des livres
	private BookRepository bookRepository = new BookRepository();

    // Emprunts en cours, indexés par le livre emprunté
    //On pourrait avoir de la persistance pour les emprunts aussi
	private Map<Livre, Emprunt> emprunts = new HashMap<>();


	public Bibliotheque() {
		initLibrary();
	}

	// To mimic loading of books from a database
	private void initLibrary() {
		addLivre(new Livre("UML", new String[] { "Booch", "Rumbaugh", "Jacobson" }, "1999", 0));
        addLivre(new Livre("UML", new String[] { "Booch", "Rumbaugh", "Jacobson" }, "1999", 4));
		addLivre(new Livre("Java", new String[] { "Gosling", "Holmes" }, "2000", 1));
		addLivre(new Livre("Design Patterns", new String[] { "Erich Gamma" }, "1994", 2));
		addLivre(new Livre("Refactoring", new String[] { "Martin Fowler" }, "1999", 3));
	}

	/************* Gestion des livres *******************/
	public void addLivre(Livre l) {
        bookRepository.save(l);
	}

    // Retourne la liste de tous les livres
    // (empruntés ou non)
    //Uniquement présents pour les tests et le déboggage mais bien sûr à supprimer dans une vraie application
	public List<Livre> getLivres() {
        Iterable<Livre> res = bookRepository.findAll();
        List<Livre> target = new ArrayList<>();
        res.forEach(target::add);
        return target;
	}

    public List<Livre> getLivresByTitle(String titre) {
        return bookRepository.getBooksByTitle(titre);
    }

    public Livre getLivreParBiblioId(String id) throws ResourceNotFoundException {
        var livre = bookRepository.getBookByLibraryId(id);
        if (livre == null) {
            throw new ResourceNotFoundException("Book not found");
        }
        return livre;
    }

	/********** Gestion des emprunts de livres **********/
	public Optional<Livre> getLivreDisponibleByTitle(String titre) {
		return bookRepository.getBooksByTitle(titre)
                        .stream()
                        .filter(l -> !l.estEmprunte())
                        .findAny();
	}

	// on considère que e et l sont non nuls et récupérés depuis un repository
	// On pourrait ajouter des vérifications
	public void emprunte(Etudiant e, Livre l) throws BookAlreadyBorrowedException {
		if (l.estEmprunte()) {
			throw new BookAlreadyBorrowedException("Book " + l.getIdDansBiblio() + " already borrowed");
		}
		Optional<Emprunt> autreExemplaireEmprunteOpt = getEmpruntsByStudent(e).stream().filter(emp -> emp.getLivreEmprunte().estUnExemplaireDuMemeLivre(l)).findFirst();
		if (autreExemplaireEmprunteOpt.isPresent()) {
			// l'étudiant emprunte déjà un autre exemplaire de ce livre
			throw new BookAlreadyBorrowedException("Same book " + autreExemplaireEmprunteOpt.get().getLivreEmprunte().getIdDansBiblio() + " already borrowed");
		}
		Emprunt emprunt = new Emprunt(LocalDate.now().plusDays(DUREE_MAX_EMPRUNT), e, l);
		emprunts.put(l, emprunt);
		l.setEstEmprunte(true);
	}

	public Emprunt getEmpruntByLivre(Livre l) {
        return emprunts.get(l);
	}

	public List<Emprunt> getEmpruntsByStudent(Etudiant e) {
		return emprunts.values().stream()
				.filter(emprunt -> emprunt.getEmprunteur().equals(e))
				.toList();
	}

	public boolean rend(Livre l) {
		if (!l.estEmprunte()) {
			return false;
		}
		Emprunt emprunt = emprunts.remove(l);
		l.setEstEmprunte(false);
		return true;
	}

	public List<Emprunt> getEmprunts() {
		return new ArrayList<>(emprunts.values());
	}

}