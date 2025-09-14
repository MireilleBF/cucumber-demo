package fr.unice.polytech.biblio.apps;

import fr.unice.polytech.biblio.api.HttpUtils;
import fr.unice.polytech.biblio.api.JaxsonUtils;
import fr.unice.polytech.biblio.api.dtos.StudentDTO;
import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.StudentRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//Inspiré de ; https://www.baeldung.com/integration-testing-a-rest-api
//Inspiré de ; https://blog.mandraketech.in/httpserver-series-testing-the-servert

//Ici on teste l'intégration de la scolarité avec la bibliothèque en utilisant un Mock de la scolarité
//Nous gardons exactement le même code que le test précédent, mais nous ajoutons un Mock de la scolarité

class IntegrationOfScolarityTest {

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
        studentRegistry = mock(StudentRegistry.class);
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
    void testBorrowBook() throws IOException, InterruptedException {
        Etudiant etudiant = new Etudiant();
        etudiant.setStudentNumber(123456);
        etudiant.setName("John Doe");
        when(studentRegistry.findByNumber(123456)).thenReturn(java.util.Optional.of(etudiant));

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

        assertEquals(HttpUtils.CREATED, response.statusCode());
        assertEquals(HttpUtils.TEXT_PLAIN, response.headers().firstValue(HttpUtils.CONTENT_TYPE).orElse(""));
        logger.log(Level.FINE, response.body());

        assertEquals("Book borrowed", response.body());
        verify(studentRegistry, times(1)).findByNumber(123456);
    }

}
