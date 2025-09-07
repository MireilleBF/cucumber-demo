package fr.unice.polytech.biblio.apps.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import fr.unice.polytech.biblio.api.ApiRegistry;
import fr.unice.polytech.biblio.api.httphandlers.GlobalExceptionHandler;
import org.mockito.Mockito;

import java.io.IOException;

class ApiRegistryTest {

        public static void main(String[] args) throws IOException {
            testDispatchSimpleGetRequest();
            testDispatchWithPathVariable();
            test404NotFound();
            System.out.println("Tous les tests sont passés !");
        }

        public static void testDispatchSimpleGetRequest() throws IOException {
            System.out.println("--- Test: Dispatch d'une requête GET simple ---");
            ApiRegistry registry = new ApiRegistry();

            // On crée un drapeau pour vérifier que le handler est bien appelé
            final boolean[] handlerCalled = {false};
            registry.registerRoute("GET", "/", (exchange, params,sender) -> {
                handlerCalled[0] = true;
            });
            //On utilise Mockito pour simuler HttpExchange
            HttpExchange exchange = Mockito.mock(HttpExchange.class);
            Mockito.when(exchange.getRequestMethod()).thenReturn("GET");
            Mockito.when(exchange.getRequestURI()).thenReturn(java.net.URI.create("/"));

            //MockHttpExchange mockExchange = new MockHttpExchange("GET", "/");
            try {
                registry.dispatch(exchange);
                if (handlerCalled[0]) {
                    System.out.println("OK: Le handler a bien été appelé.");
                } else {
                    System.err.println("Échec: Le handler n'a pas été appelé.");
                }
            } catch (Exception e) {
                //e.printStackTrace();
                GlobalExceptionHandler.handleException(exchange, e);
            }
        }

       public static void testDispatchWithPathVariable() {
            System.out.println("\n--- Test: Dispatch avec une variable de chemin ---");
            ApiRegistry registry = new ApiRegistry();

            // On vérifie que les paramètres sont correctement extraits
            final String[] extractedId = {null};
            //A l'appel du handler, on stocke la variable de chemin extraite
            registry.registerRoute("GET", "/users/{id}", (exchange, params, sender) -> {
                extractedId[0] = params.get("id");
            });

            //On utilise Mockito pour simuler HttpExchange
           HttpExchange exchange = Mockito.mock(HttpExchange.class);
           Mockito.when(exchange.getRequestMethod()).thenReturn("GET");
           Mockito.when(exchange.getRequestURI()).thenReturn(java.net.URI.create("/users/123"));

          try {
                registry.dispatch(exchange);
                if ("123".equals(extractedId[0])) {
                    System.out.println(" OK: La variable de chemin a été extraite: " + extractedId[0]);
                } else {
                    System.err.println("Échec: Variable de chemin incorrecte. Attendue: 123, Obtenue: " + extractedId[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void test404NotFound() {
            System.out.println("\n--- Test: Route non trouvée (404) ---");
            ApiRegistry registry = new ApiRegistry();

            // Le routeur ne contient aucune route. On attend un 404.
            //On utilise Mockito pour simuler HttpExchange
            HttpExchange exchange = Mockito.mock(HttpExchange.class);
            Mockito.when(exchange.getRequestMethod()).thenReturn("GET");
            Mockito.when(exchange.getRequestURI()).thenReturn(java.net.URI.create("/non-existent-path"));
            Mockito.when(exchange.getResponseBody()).thenReturn(new java.io.ByteArrayOutputStream());

            try {
                registry.dispatch(exchange);
                String responseBody = exchange.getResponseBody().toString();
                if ("Route not found".equals(responseBody)) {
                    System.out.println("OK: La réponse 404 a été correctement renvoyée.");
                } else {
                    System.err.println("Échec: Réponse incorrecte. Obtenue: " + responseBody);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}