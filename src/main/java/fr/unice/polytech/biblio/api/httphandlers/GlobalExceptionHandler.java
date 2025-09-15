package fr.unice.polytech.biblio.api.httphandlers;


import com.sun.net.httpserver.HttpExchange;
import fr.unice.polytech.biblio.api.HttpUtils;
import fr.unice.polytech.biblio.services.exceptions.BookAlreadyBorrowedException;
import fr.unice.polytech.biblio.services.exceptions.ResourceAlreadyExistsException;
import fr.unice.polytech.biblio.services.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/****
 * This class is a global exception handler for HTTP requests.
 * It catches different types of exceptions and sends appropriate HTTP responses.
 */
public class GlobalExceptionHandler {

    private GlobalExceptionHandler() {
        logger.setLevel(Level.OFF);
    }

    static Logger logger = Logger.getLogger("GlobalExceptionHandler");

    public static void handleException(HttpExchange exchange, ResourceNotFoundException e) throws IOException {
        logger.info("ResourceNotFoundException caught: " + e.getMessage());
        sendErrorResponse(exchange, HttpUtils.RESOURCE_NOT_FOUND, e.getMessage());
    }

    public static void handleException(HttpExchange exchange, ResourceAlreadyExistsException e) throws IOException {
        logger.info("ResourceAlreadyExistsException caught: " + e.getMessage());
        sendErrorResponse(exchange, HttpUtils.CONFLICT, e.getMessage());
    }

    public static void handleException(HttpExchange exchange, BookAlreadyBorrowedException e) throws IOException {
        logger.info("BookAlreadyBorrowedException caught: " + e.getMessage());
        sendErrorResponse(exchange, HttpUtils.UNPROCESSABLE_ENTITY, e.getMessage());
    }

    public static void handleException(HttpExchange exchange, IllegalArgumentException e) throws IOException {
        logger.info("IllegalArgumentException caught: " + e.getMessage());
        sendErrorResponse(exchange, HttpUtils.BAD_REQUEST, e.getMessage());
    }

    public static void handleException(HttpExchange exchange, Exception e) throws IOException {
        logger.info("Exception caught: " + e.getMessage());
        sendErrorResponse(exchange, HttpUtils.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    public static <R> R callWithGlobalExceptionHandling(HttpExchange exchange, Callable<R> callable) throws IOException {
        try {
            return callable.call();
        } catch (ResourceNotFoundException e) {
            handleException(exchange, e);
        } catch (ResourceAlreadyExistsException e) {
            handleException(exchange, e);
        } catch (BookAlreadyBorrowedException e) {
            handleException(exchange, e);
        } catch (IllegalArgumentException e) {
            handleException(exchange, e);
        } catch (Exception e) {
            handleException(exchange, e);
        }
        return null;
    }

    private static void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set(HttpUtils.CONTENT_TYPE, HttpUtils.APPLICATION_JSON);
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
