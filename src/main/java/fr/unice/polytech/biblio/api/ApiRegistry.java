package fr.unice.polytech.biblio.api;

import com.sun.net.httpserver.HttpExchange;
import fr.unice.polytech.biblio.api.httphandlers.GlobalExceptionHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/****
 * This class is used to register API routes and dispatch incoming HTTP requests
 * to the appropriate handlers based on the HTTP method and path.
 * It supports path parameters in the form of {paramName}.
 * Example: /users/{id} will match /users/123 and extract id=123
 */
public class ApiRegistry {

    // Classe interne pour stocker les informations de chaque route enregistrée
    // (pattern, noms des paramètres, gestionnaire de routes)
    public record RouteEntry(Pattern pattern, List<String> paramNames, RouteHandler handler) {}

    // Map des routes : méthode HTTP → liste des entrées de route (eg. GET → [RouteEntry1, RouteEntry2, ...])
    private final Map<String, List<RouteEntry>> routes = new HashMap<>();

    /****
     * Enregistre une nouvelle route avec la méthode HTTP, le chemin et le gestionnaire de la requete
     * @param method la méthode HTTP (GET, POST, etc.)
     * @param path le chemin de la route, pouvant contenir des paramètres entre accolades (e.g. /users/{id})
     * @param handler le gestionnaire de la route, une fonction qui prend HttpExchange, les paramètres extraits et un ResponseSender
     * Exemple d'utilisation :
     * registry.registerRoute("GET", "/users/{id}", (exchange, params,sender) -> {
     *     String userId = params.get("id");
     *     // Handle the request...
     */
    public void registerRoute(String method, String path, RouteHandler handler) {
        // Find parameter names in the path template : Pour cela on utilise une regex pour trouver les paramètres dans le chemin i.e {id}
        Pattern paramPattern = Pattern.compile("\\{([^/]+)}");
        //On crée un "matcher" pour trouver les paramètres.
        Matcher paramMatcher = paramPattern.matcher(path);
        List<String> paramNames = new ArrayList<>();
        while (paramMatcher.find()) {
            paramNames.add(paramMatcher.group(1));
        }
        //paramNames contient maintenant la liste des noms de paramètres dans l'ordre d'apparition dans le chemin

        // Create a regex pattern to match the values
        //On l'utilisera pour extraire les valeurs des paramètres dans le chemin de la requête
        String regexPath = path.replaceAll("\\{[^/]+}", "([^/]+)");
        Pattern pattern = Pattern.compile(regexPath);

        //On ajoute la route au registre
        //si la méthode n'existe pas, on crée une nouvelle liste pour cette méthode
        // puis on ajoute une nouvelle RouteEntry dans cette liste
        // Si la méthode existe déjà, on ajoute simplement une nouvelle RouteEntry.
        routes.computeIfAbsent(method, k -> new ArrayList<>()).add(
                new RouteEntry(pattern, paramNames, handler)
        );
    }

    // Méthode pour dispatcher une requête entrante.
    // Elle cherche la route correspondante et appelle le gestionnaire avec les paramètres extraits
    public void dispatch(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String requestPath = exchange.getRequestURI().getPath();

        // Récupère les routes pour la méthode HTTP donnée
        List<RouteEntry> methodRoutes = routes.getOrDefault(requestMethod, new ArrayList<>());

        // Cherche une route qui correspond au chemin de la requête (elle doit matcher le pattern)
        for (RouteEntry entry : methodRoutes) {
            Matcher matcher = entry.pattern().matcher(requestPath);

            if (matcher.matches()) {
                Map<String, String> pathParams = new HashMap<>();
                for (int i = 0; i < entry.paramNames().size(); i++) {
                    String paramName = entry.paramNames().get(i);
                    String paramValue = matcher.group(i + 1);
                    pathParams.put(paramName, paramValue);
                }

                GlobalExceptionHandler.callWithGlobalExceptionHandling(exchange, () -> {
                    // Ici, on crée une instance de ResponseSender qui appelle la méthode privée ; les parametres lui sont passés à partir du handler.
                    ResponseSender sender = (statusCode, response, headerParams) -> sendResponse(exchange, statusCode, response, headerParams);
                    entry.handler().handle(exchange, pathParams, sender);
                    return null;
                });
                return;
            }
        }
        // If no route matches
        sendResponse(exchange, HttpUtils.RESOURCE_NOT_FOUND, "Route not found", null);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response, Map<String, String> headers) throws IOException {
//On généralise les en-têtes CORS pour toutes les réponses
        //exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); // Remplacez par votre origine cliente
        //exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
        //exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Accept, X-Requested-With, Content-Type, Content-Length, Accept-Encoding, X-CSRF-Token, Authorization");

        // Ajoute les en-têtes personnalisés
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                exchange.getResponseHeaders().set(header.getKey(), header.getValue());
            }
        }
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }

    }
}