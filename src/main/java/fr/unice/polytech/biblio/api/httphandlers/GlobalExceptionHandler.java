package fr.unice.polytech.biblio.api.httphandlers;


import com.sun.net.httpserver.HttpExchange;
import fr.unice.polytech.biblio.api.HttpUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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

    public static void handleException(HttpExchange exchange, Exception e) throws IOException {
        logger.info("Exception caught: " + e.getMessage());
        sendErrorResponse(exchange, HttpUtils.NOT_FOUND_RESOURCE, e.getMessage());
    }

    public static void handleException(HttpExchange exchange, IllegalArgumentException e) throws IOException {
        logger.info("IllegalArgumentException caught: " + e.getMessage());
        sendErrorResponse(exchange, HttpUtils.BAD_REQUEST, e.getMessage());
    }

    public static void handleException(HttpExchange exchange, RuntimeException e) throws IOException {
        logger.info("RuntimeException caught: " + e.getMessage());
        sendErrorResponse(exchange, HttpUtils.INTERNAL_SERVER_ERROR_CODE, "Internal Server Error");
    }

    private static void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
