package fr.unice.polytech.biblio.services;

/* BookNotFoundException is a custom exception that is thrown when a book is not found in the library */

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}
