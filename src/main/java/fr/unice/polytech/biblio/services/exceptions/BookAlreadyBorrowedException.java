package fr.unice.polytech.biblio.services.exceptions;

public class BookAlreadyBorrowedException extends Exception {
    public BookAlreadyBorrowedException(String message) {
        super(message);
        }
}
