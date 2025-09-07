package fr.unice.polytech.biblio.api.httphandlers;

public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
