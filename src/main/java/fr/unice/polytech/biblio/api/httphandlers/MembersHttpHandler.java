package fr.unice.polytech.biblio.api.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.biblio.api.HttpUtils;
import fr.unice.polytech.biblio.services.StudentRegistry;
import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.api.JaxsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class is used to manage the members of the university.
 * It handles the following requests:
 * - GET /api/members : return the list of all members
 * - POST /api/members : add a new member
 * - GET /api/members/{id} : return the member with the given id
 * - PUT /api/members/{id} : update the member with the given id
 * - DELETE /api/members/{id} : delete the member with the given id
 * It will be able to manage the following members:
 * - id : the identifier of the member (student number)
 * - name : the name of the member
 * - email : the email of the member
 *
 *
 */
public class MembersHttpHandler implements HttpHandler {

    private final StudentRegistry studentRegistry;


    static Logger logger = Logger.getLogger("MembersHttpHandler");

    static {
        logger.setLevel(Level.OFF);
    }

    public MembersHttpHandler(StudentRegistry studentRegistry) {
        this.studentRegistry = studentRegistry;
    }


    /*****
     * Handles the incoming HTTP request.
     * The method supports GET, POST, PUT, DELETE and OPTIONS methods.
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     * On a fait le choix de ne pas faire appel à une API Registry
     * pour la gestion des membres, pour garder le code simple et surtout "transparent"
     */
    @Override
    public void handle(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
        logger.log(java.util.logging.Level.FINE, "MembersHandler called");


        // CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); // Remplacez par votre origine cliente
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Accept, X-Requested-With, Content-Type, Content-Length, Accept-Encoding, X-CSRF-Token, Authorization");


        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET":
                    logger.log(java.util.logging.Level.FINE, "GET method called");
                    if (exchange.getRequestURI().getPath().equals("/api/members")) {
                        logger.log(java.util.logging.Level.FINE, "GET all members");
                        answerWithAllMembers(exchange);
                    } else {
                        String id = exchange.getRequestURI().getPath().substring("/api/members/".length());
                        logger.log(java.util.logging.Level.FINE, "GET member with id " + id);
                        answerWithMember(exchange, id);
                    }
                    break;
                case "POST":
                    logger.log(java.util.logging.Level.FINE, "POST method called");
                    askToCreateMember(exchange);
                    break;
                case "PUT":
                    logger.log(java.util.logging.Level.FINE, "PUT method called");
                    askToUpdateMember(exchange);
                    break;
                case "DELETE":
                    logger.log(java.util.logging.Level.FINE, "DELETE method called");
                    askToDeleteMember(exchange);
                    break;
                case "OPTIONS":
                    //for CORS preflight
                    exchange.sendResponseHeaders(200, -1);
                    break;
                default:
                    logger.log(java.util.logging.Level.FINE, "Method not supported");
                    break;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while processing the request", e);
            GlobalExceptionHandler.handleException(exchange, e);
        }
    }

    //No need to return a response for DELETE (204)
    private void askToDeleteMember(HttpExchange exchange) throws IOException {
        String id = exchange.getRequestURI().getPath().substring("/api/members/".length());
        studentRegistry.removeStudent(Integer.parseInt(id));
        exchange.sendResponseHeaders(204, 0);
        exchange.getResponseBody().close();
    }

    private void askToUpdateMember(HttpExchange exchange) throws IOException {
        //get the request body, it contains the updated member as a JSON string
        InputStream is = exchange.getRequestBody();
        String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        logger.log(java.util.logging.Level.FINE, "Request body: " + json);

        //build the updated member from the JSON string
        Etudiant etudiant = JaxsonUtils.fromJson(json, Etudiant.class);
        if (etudiant == null) {
            throw new IllegalArgumentException("Invalid member from json");
        }
        //update the member in the list of members
        //if the member does not exist, it will be added
        //if the member exists, we only support name update
        studentRegistry.updateStudent(etudiant.getStudentNumber(), etudiant.getName());
        //build the response
        String response = "Member updated successfully.";
        //send the response to the client
        exchange.getResponseHeaders().set(HttpUtils.CONTENT_TYPE, HttpUtils.TEXT_PLAIN);
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void askToCreateMember(HttpExchange exchange) throws IOException {
        //get the request body, it contains the new member to add as a JSON string
        InputStream is = exchange.getRequestBody();
        String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        logger.log(java.util.logging.Level.FINE, "Request body: " + json);

        //build the new member from the JSON string
        Etudiant etudiant = JaxsonUtils.fromJson(json, Etudiant.class);
        if (etudiant == null) {
            throw new IllegalArgumentException("Invalid member from json");
        }
        //add the new member to the list of members
        studentRegistry.addStudent(etudiant.getName(), etudiant.getStudentNumber());

        //build the response
        String response = "Member added successfully.";
        //send the response to the client
        exchange.getResponseHeaders().set(HttpUtils.CONTENT_TYPE, HttpUtils.TEXT_PLAIN);
        exchange.sendResponseHeaders(201, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void answerWithMember(HttpExchange exchange, String id) throws IOException {
        Etudiant etudiant = studentRegistry.findByNumber(Integer.parseInt(id)).orElse(null);
        if (etudiant == null) {
            exchange.sendResponseHeaders(404, 0);
            exchange.getResponseBody().close();

        } else {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            String response = JaxsonUtils.toJson(etudiant);
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
    }

    private void answerWithAllMembers(HttpExchange exchange) throws IOException {
        List<Etudiant> etudiants = studentRegistry.findAll();

        //create the response
        StringBuilder response = new StringBuilder("List of all members : \n");
        for (Etudiant etudiant : etudiants) {
            response.append(etudiant.toString()).append("\n");
        }
        //send the response to the client
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.toString().getBytes());
        exchange.getResponseBody().close();
    }


}
