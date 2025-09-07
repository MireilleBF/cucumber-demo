package fr.unice.polytech.biblio.apps;

import fr.unice.polytech.biblio.api.JaxsonUtils;
import fr.unice.polytech.biblio.api.dtos.StudentDTO;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.BookNotFoundException;
import fr.unice.polytech.biblio.services.StudentRegistry;
import fr.unice.polytech.biblio.entities.Livre;
import fr.unice.polytech.biblio.api.HttpUtils;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Inspiré de ; https://www.baeldung.com/integration-testing-a-rest-api
//Inspiré de ; https://blog.mandraketech.in/httpserver-series-testing-the-servert

class LibraryHttpHandlerTest {

        private static final int PORT = 8003;
        private static final String BASE_URL = "http://localhost:" + PORT + "/api/library";

        StudentRegistry studentRegistry;
        Bibliotheque biblio;

        static Logger logger = Logger.getLogger("LibraryHttpHandlerTest");

        static {
                logger.setLevel(Level.OFF);
        }

        @BeforeEach
        public void setup() throws IOException {
                logger.info("Je démarre le serveur");
                studentRegistry = new StudentRegistry();
                biblio = new Bibliotheque();
                SimpleHttpServer4Library.startServer(PORT, biblio, studentRegistry);

        }

        @AfterEach
        public void teardown() {
                // Arrêter le serveur après les tests
                logger.info("J arrete le serveur");
                SimpleHttpServer4Library.stopServer(8003);
        }

        @Test
        void testGetBook() throws IOException, InterruptedException, BookNotFoundException {
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL + "/J-1");
                // Only to check there is no problem with the biblio
                logger.info(biblio.getLivres().toString());
                logger.log(Level.FINE, "book : " + biblio.getLivreParBiblioId("J-1"));
                Livre l = biblio.getLivreParBiblioId("J-1");
                assertEquals("Java", l.getTitre());
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .GET()
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String jsonMimeType = "application/json";
                assertEquals(200, response.statusCode());
                assertEquals(jsonMimeType, response.headers().firstValue("Content-Type").orElse(""));
                String json = response.body();
                logger.log(Level.FINE, "book : " + json);
                assertTrue(json.contains("Java"));
                Livre livre = JaxsonUtils.fromJson(response.body(), Livre.class);
                assertEquals("Java", livre.getTitre());
                assertEquals("J-1", livre.getIdDansBiblio());
                assertEquals("2000", livre.getIsbn());
        }

        // Tester le cas où le livre n'existe pas
        @Test
        void testGetBookNotFound() throws IOException, InterruptedException {
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL + "/J-100");
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .GET()
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String jsonMimeType = "application/json";
                assertEquals(404, response.statusCode());
                assertEquals(jsonMimeType, response.headers().firstValue("Content-Type").orElse(""));
                assertEquals("{\"error\": \"Book not found\"}", response.body());
        }

        @Test
        void testListBooks() throws IOException, InterruptedException {
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL);
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .GET()
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String jsonMimeType = "application/json";
                assertEquals(200, response.statusCode());
                assertEquals(jsonMimeType, response.headers().firstValue("Content-Type").orElse(""));
                logger.log(Level.FINE, response.body());
                assertTrue(response.body().contains("Design Patterns"));
        }

        @Test
        void testaddBook() throws IOException, InterruptedException {
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL);
                var compteur = 7;

                Livre livre = new Livre("Your Code as a Crime Scene", new String[] { "Adam Tornhill" }, "123",
                                compteur);
                String newBook = JaxsonUtils.toJson(livre);
                logger.log(Level.FINE, "Json before : " + newBook);

                var response = client.send(
                                HttpRequest.newBuilder()
                                                .POST(HttpRequest.BodyPublishers.ofString(newBook))
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String jsonMimeType = "text/plain";
                assertEquals(201, response.statusCode());
                assertEquals(jsonMimeType, response.headers().firstValue("Content-Type").orElse(""));
                assertEquals("Book created", response.body());

                // Testing the payload
        }

        @Test
        void testBorrowBook() throws IOException, InterruptedException {
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL + "/J-1/borrow");
                StudentDTO dto = new StudentDTO(123456);
                String jsonDTO = JaxsonUtils.toJson(dto);
                logger.log(Level.FINE, "Json before : " + jsonDTO);
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .POST(HttpRequest.BodyPublishers.ofString(jsonDTO))
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String jsonMimeType = "text/plain";
                assertEquals(201, response.statusCode());
                assertEquals(jsonMimeType, response.headers().firstValue("Content-Type").orElse(""));
                logger.log(Level.FINE, response.body());

                assertEquals("Book borrowed", response.body());

        }

        // Test the case where the book cannot be borrowed
        @Test
        void testBorrowBookError() throws IOException, InterruptedException {
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL + "/J-100/borrow");
                StudentDTO dto = new StudentDTO(123456);
                String jsonDTO = JaxsonUtils.toJson(dto);
                logger.log(Level.INFO, "Json before : " + jsonDTO);
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .POST(HttpRequest.BodyPublishers.ofString(jsonDTO))
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String jsonMimeType = "application/json";
                assertEquals(HttpUtils.NOT_FOUND_RESOURCE, response.statusCode());
                assertEquals(jsonMimeType, response.headers().firstValue("Content-Type").orElse(""));
                logger.log(Level.FINE, response.body());

                assertEquals("{\"error\": \"Book not found\"}", response.body());
        }
}
