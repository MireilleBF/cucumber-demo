package fr.unice.polytech.biblio.api;

import com.sun.net.httpserver.HttpExchange;

import java.util.Map;
    @FunctionalInterface
    public interface RouteHandler {
        void handle(HttpExchange exchange, Map<String, String> pathParams, ResponseSender sender  ) throws Exception;
    }
