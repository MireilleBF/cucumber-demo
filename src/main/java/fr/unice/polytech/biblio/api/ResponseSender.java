package fr.unice.polytech.biblio.api;

import java.io.IOException;
import java.util.Map;

@FunctionalInterface
public interface ResponseSender {
    void send(int statusCode, String response, Map<String, String> headers) throws IOException;
}