package fr.unice.polytech.biblio.api.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.biblio.api.ApiRegistry;
import fr.unice.polytech.biblio.api.HttpUtils;
import fr.unice.polytech.biblio.api.JaxsonUtils;
import fr.unice.polytech.biblio.api.ResponseSender;
import fr.unice.polytech.biblio.api.dtos.StudentDTO;
import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.entities.Livre;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.StudentRegistry;
import fr.unice.polytech.biblio.services.exceptions.BookAlreadyBorrowedException;
import fr.unice.polytech.biblio.services.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Mireille Blay-Fornarino
 */

/**
 * This class is used to manage the library.
 * It handles the following requests:
 * - GET /api/library : return the list of all books
 * - POST /api/library : add a new book
 * - GET /api/library/{id} : return the book with the given id
 * <p>
 * and to borrow a book
 * - POST /api/library/{id}/borrow : borrow the book with the given id to the student with the given student number
 */
public class LibraryHttpHandler implements HttpHandler {
    private final ApiRegistry apiRegistry = new ApiRegistry();

    private final Bibliotheque bibliotheque;
    private final StudentRegistry studentRegistry;

    static Logger logger = Logger.getLogger("LibraryHttpHandler");

    static {
        logger.setLevel(Level.OFF);
    }


    public LibraryHttpHandler(Bibliotheque bibliotheque, StudentRegistry studentRegistry) {
        this.bibliotheque = bibliotheque;
        this.studentRegistry = studentRegistry;
        initializeApiRegistry();
    }

    private void initializeApiRegistry() {
        apiRegistry.registerRoute("GET", "/api/library",
                (exchange, pathParams,sender) -> {
                        answerWithAllBooks(exchange,sender);
                    });
        apiRegistry.registerRoute("GET", "/api/library/{id}",
                (exchange, pathParams,sender) -> {
                    String id = pathParams.get("id");
                    validateId(id);
                    answerWithBook(exchange, id,sender);
                });
        apiRegistry.registerRoute("POST", "/api/library",
                (exchange, pathParams,sender ) -> askToCreateBook(exchange, sender));
        apiRegistry.registerRoute("POST", "/api/library/{id}/borrow",
                (exchange, pathParams, sender) -> {
                    String id = pathParams.get("id");
                    validateId(id);
                    askToBorrowBook(exchange, id,sender);
                });

       apiRegistry.registerRoute("OPTIONS", "/api/library",
                (exchange, pathParams, sender) -> {
                    Map<String, String> headers = new HashMap<>();
                    sender.send(HttpUtils.OK, "", headers);
                });

        apiRegistry.registerRoute("OPTIONS", "/api/library/{id}/borrow",
                (exchange, pathParams, sender) -> {
                    Map<String, String> headers = new HashMap<>();
                    sender.send(HttpUtils.OK, "", headers);
                });
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("LibraryHandler called");
        // CORS - remontée dans le sender
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Accept, X-Requested-With, Content-Type, Content-Length, Accept-Encoding, X-CSRF-Token, Authorization");

        apiRegistry.dispatch(exchange);
    }


    public void askToBorrowBook(HttpExchange exchange, String bookId, ResponseSender sender)
            throws IOException, ResourceNotFoundException, BookAlreadyBorrowedException {
            InputStream is = exchange.getRequestBody();
            String jsonBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            StudentDTO sto = JaxsonUtils.fromJson(jsonBody, StudentDTO.class);
            validateStudentDTO(sto);

            int studentNumber = sto.studentNumber();
            //It can throw a ResourceNotFoundException
            Livre book = bibliotheque.getLivreParBiblioId(bookId);

            var student = studentRegistry.findByNumber(studentNumber);
            if (student.isEmpty()) {
                throw  new ResourceNotFoundException("This student does not exist");
            }

            Etudiant e = student.get();
            // It can throw an Exception if the book is not available
            // or if the same book is already borrowed by the student
            bibliotheque.emprunte(e, book);
            logger.log(Level.FINE, "Emprunt effectué");
            //build the response
            String response = "Book borrowed";
            //send the response to the client
            Map<String, String> headers = new HashMap<>();
            headers.put(HttpUtils.CONTENT_TYPE, HttpUtils.TEXT_PLAIN);
            sender.send(HttpUtils.CREATED,response,headers);
    }


    public void askToCreateBook(HttpExchange exchange, ResponseSender sender)
            throws IOException {
            InputStream is = exchange.getRequestBody();
            String jsonBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            Livre livre = JaxsonUtils.fromJson(jsonBody, Livre.class);
            if (livre == null) {
                throw new IllegalArgumentException("Invalid book data");
            }
            bibliotheque.addLivre(livre);
            //build the response
            Map<String, String> headers = new HashMap<>();
            headers.put(HttpUtils.CONTENT_TYPE, HttpUtils.TEXT_PLAIN);
            String response = "Book created";
            sender.send(HttpUtils.CREATED,response,headers);
    }


    public void answerWithBook(HttpExchange exchange, String id, ResponseSender sender)
            throws IOException, ResourceNotFoundException {
            Livre livre = bibliotheque.getLivreParBiblioId(id);

            Map<String, String> headers = new HashMap<>();
            headers.put(HttpUtils.CONTENT_TYPE,HttpUtils.APPLICATION_JSON);

            String response = JaxsonUtils.toJson(livre);
            sender.send(HttpUtils.OK,response,headers);
    }

    public void answerWithAllBooks(HttpExchange exchange,ResponseSender responseSender)
            throws IOException {
        List<Livre> livres = bibliotheque.getLivres();

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpUtils.CONTENT_TYPE, HttpUtils.APPLICATION_JSON);

        String response = JaxsonUtils.toJson(livres);
        responseSender.send(HttpUtils.OK, response,headers);
    }


    /***** Validation methods *****/
    private void validateId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
    }

    private void validateStudentDTO(StudentDTO dto) {
        if (dto == null || dto.studentNumber() <= 0) {
            throw new IllegalArgumentException("Invalid student data");
        }
    }


}
