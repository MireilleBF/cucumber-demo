package fr.unice.polytech.biblio.repositories;

import fr.unice.polytech.biblio.entities.Livre;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    private void initLibrary(BookRepository repo) {
        repo.save(new Livre("UML", new String[] { "Booch", "Rumbaugh", "Jacobson" }, "1999", 0));
        repo.save(new Livre("UML", new String[] { "Booch", "Rumbaugh", "Jacobson" }, "1999", 1));
        repo.save(new Livre("Java", new String[] { "Gosling", "Holmes" }, "2000", 1));
        repo.save(new Livre("Design Patterns", new String[] { "Erich Gamma" }, "1994", 2));
        repo.save(new Livre("Refactoring", new String[] { "Martin Fowler" }, "1999", 3));
    }
    @Test
    void findAllEmpty() {
        BookRepository bookRepository = new BookRepository();
        assertNotNull(bookRepository.findAll());
    }

    @Test
    void findAll() {
        BookRepository bookRepository = new BookRepository();
        initLibrary(bookRepository);
        Iterable<Livre> books = bookRepository.findAll();
        ArrayList<Livre> booksList = new ArrayList<Livre>();
        books.forEach(booksList::add);
        assertEquals(5, booksList.size());
    }

    @Test
    void getBooksByTitle() {
        BookRepository bookRepository = new BookRepository();
        initLibrary(bookRepository);

        assertEquals(2, bookRepository.getBooksByTitle("UML").size());
        System.out.println(bookRepository.getBooksByTitle("UML"));
        assertEquals(1, bookRepository.getBooksByTitle("Java").size());
        assertEquals(0, bookRepository.getBooksByTitle("C++").size());
    }

}