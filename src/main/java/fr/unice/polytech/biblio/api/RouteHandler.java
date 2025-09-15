package fr.unice.polytech.biblio.api;

import com.sun.net.httpserver.HttpExchange;
import fr.unice.polytech.biblio.services.exceptions.BookAlreadyBorrowedException;

import java.util.Map;
    @FunctionalInterface
    public interface RouteHandler {
        void handle(HttpExchange exchange, Map<String, String> pathParams, ResponseSender sender  ) throws Exception, BookAlreadyBorrowedException;
    }
