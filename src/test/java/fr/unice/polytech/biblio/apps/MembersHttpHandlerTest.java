package fr.unice.polytech.biblio.apps;

import fr.unice.polytech.biblio.api.JaxsonUtils;
import fr.unice.polytech.biblio.entities.Etudiant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MembersHttpHandlerTest {

        private int PORT = 8002;
        private final String BASE_URL = "http://localhost:" + PORT + "/api/members";

        /**
         * Start the server before each test
         * This will allow to test the server but the cost is that the server will be
         * started and stopped for each test
         * To avoid conflict with other servers, we look for a free port
         * @throws IOException
         */
        @BeforeEach
        void setUp() throws IOException {
                PORT = SimpleHttpServer4Library.findFreePortFrom(PORT);
                SimpleHttpServer4Scolarity.startServer(PORT);
        }

        @AfterEach
        void tearDown() {
            SimpleHttpServer4Scolarity.stopServer(PORT);
        }

        @Test
        void testAnswerWithAllMembers() throws IOException, InterruptedException {
                // Build the request
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL);
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .GET()
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String textMimeType = "text/plain";
                assertEquals(200, response.statusCode());
                assertEquals(textMimeType, response.headers().firstValue("Content-Type").orElse(""));
                assertTrue(response.body().contains("List of all members :"));
                assertTrue(response.body().contains("John Doe"));
                assertTrue(response.body().contains("Jane Doe"));
        }

        @Test
        void testAnswerWithMember() throws IOException, InterruptedException {
                int studentNumber = 123456;
                String expectedName = "John Doe";
                testGetAMemberById(studentNumber, expectedName);
        }

        private void testGetAMemberById(int studentNumber, String expectedName)
                        throws IOException, InterruptedException {
                // Build the request
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL + "/" + studentNumber);
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .GET()
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String returnMimeType = "application/json";
                assertEquals(200, response.statusCode());
                assertEquals(returnMimeType, response.headers().firstValue("Content-Type").orElse(""));
                String json = response.body();
                assertTrue(json.contains(expectedName));
                assertTrue(json.contains(Integer.toString(studentNumber)));
                JaxsonUtils.fromJson(json, Etudiant.class);
        }

        @Test
        void testAskToCreateMember() throws IOException, InterruptedException {
                // Build the request
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL);
                String json = "{\"name\":\"Pierre Dupond\",\"studentNumber\":999}";
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .POST(HttpRequest.BodyPublishers.ofString(json))
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String textMimeType = "text/plain";
                assertEquals(201, response.statusCode());
                assertEquals(textMimeType, response.headers().firstValue("Content-Type").orElse(""));
                assertTrue(response.body().contains("Member added successfully."));

                // Now we test that the member has been added
                testGetAMemberById(999, "Pierre Dupond");
        }

        @Test
        void testAskToUpdateMember() throws IOException, InterruptedException {
                // Build the request
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL + "/123456");
                String json = "{\"name\":\"Baltazar Doe\",\"studentNumber\":123456}";
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .PUT(HttpRequest.BodyPublishers.ofString(json))
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                String textMimeType = "text/plain";
                assertEquals(200, response.statusCode());
                assertEquals(textMimeType, response.headers().firstValue("Content-Type").orElse(""));
                assertTrue(response.body().contains("Member updated successfully."));

                // Now we test that the member has been updated
                testGetAMemberById(123456, "Baltazar Doe");
        }

        @Test
        void testAskToDeleteMember() throws IOException, InterruptedException {
                // Build the request
                var client = HttpClient.newHttpClient();
                var uri = URI.create(BASE_URL + "/123456");
                var response = client.send(
                                HttpRequest.newBuilder()
                                                .DELETE()
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                assertEquals(204, response.statusCode());

                // Now we test that the member has been deleted
                // Build the request
                uri = URI.create(BASE_URL + "/123456");
                response = client.send(
                                HttpRequest.newBuilder()
                                                .GET()
                                                .uri(uri)
                                                .build(),
                                HttpResponse.BodyHandlers.ofString());

                assertEquals(404, response.statusCode());
        }
}