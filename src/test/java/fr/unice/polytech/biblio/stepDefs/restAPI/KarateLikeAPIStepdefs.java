package fr.unice.polytech.biblio.stepDefs.restAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.biblio.api.JaxsonUtils;
import fr.unice.polytech.biblio.entities.Livre;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.StudentRegistry;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KarateLikeAPIStepdefs {

    static Logger logger = Logger.getLogger("KarateLikeAPITesting");
    {
        logger.setLevel(Level.FINE);
    }

    private static String url4library;
    StudentRegistry studentRegistry;
    Bibliotheque biblio;

    @Given("the Karate setup is done, servers are configured and started")
    public void theTestServersAreConfiguredAndStarted() {
        url4library = TestContext.getBASE_URL4LIBRARY();
        studentRegistry = TestContext.getStudentRegistry();
        biblio = TestContext.getBiblio();
    }

    /*
     * Given url url4library
     * Given request { title: "Guernica", author: [ "Dave Boling"], isbn:
     * "2-84893-019-5", identifiant: "G-0" }
     * When method post
     * Then status 200
     * And match response == { "Book created" }
     */

    String url;
    URI uri;
    HttpClient client;

    @Given("url url4library")
    public void urlUrlLibraryG() {
        logger.info("K-Given: url url4Library");
        url = url4library;
        uri = URI.create(url);
        client = HttpClient.newHttpClient();
        logger.info("End - K-Given : \"url url4library\" = " + url);
    }

    HttpRequest.BodyPublisher body;

    @Given("request \\{ title: {string}, author: [ {string}], isbn: {string}, identifiant: {string} }")
    public void request_author_isbn_identifiant(String title, String author, String isbn, String identifiant)
            throws JsonProcessingException {
        logger.info("K-Given : request title, author, isbn, identifiant: " + title + " " + author + " " + isbn + " " +
                identifiant);
        Livre livre = Livre.createLivre(title, identifiant);
        livre.setIsbn(isbn);
        livre.setAuteurs(new String[] { author });
        String newBook = JaxsonUtils.toJson(livre);
        System.out.println(newBook);
        logger.log(Level.FINE, "Json before : {0}", newBook);
        body = HttpRequest.BodyPublishers.ofString(newBook);
        logger.info("End - K-Given : request author isbn: " + newBook);
    }

    HttpResponse<String> response;

    @When("method post")
    public void method_post() throws IOException, InterruptedException {
        logger.fine("K-When : When method post to url " + url);
        response = client.send(
                HttpRequest.newBuilder()
                        .POST(body)
                        .uri(uri)
                        .build(),
                HttpResponse.BodyHandlers.ofString());
        logger.info("KARATE : response status " + response.statusCode());
    }

    @Then("status {int}")
    public void status(Integer int1) {
        assertEquals(int1, response.statusCode());
    }

    @Then("match response == \\{ {string} }")
    public void match_response(String contents) {
        assertEquals(contents, response.body());
    }

    /**
     * # get by id
     * Given url4library+ '/G-0'
     * When method get
     * Then status 200
     * And match response ==
     * {"titre":"Java","auteurs":["Gosling","Holmes"],"isbn":"2000","identifiant":"J-1"}
     */

    @Given("url url4library+ {string}")
    public void url4library(String complement) {
        url = url4library + complement;
        uri = URI.create(url);
        logger.info("KARATE : url4library+ " + url);
    }

    @When("method get")
    public void method_get() {
        try {
            response = client.send(
                    HttpRequest.newBuilder()
                            .GET()
                            .uri(uri)
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            logger.info("KARATE : GET response status " + response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("match response == \\{titre:{string},auteurs:[{string}],isbn: {string},identifiant:{string}}")
    public void match_response_auteurs_isbn_identifiant(String title, String author, String isbn, String identifiant) {
        assertEquals("{\"titre\":\"" + title + "\",\"auteurs\":[\"" + author + "\"],\"isbn\":\"" + isbn
                + "\",\"idDansBiblio\":\"" + identifiant + "\"}", response.body());
    }

    @Then("match response contains \\{titre:{string},auteurs:[{string}],isbn: {string},identifiant:{string}}")
    public void match_response_contains_auteurs_isbn_identifiant(String title, String author, String isbn,
            String identifiant) {
        assertTrue(response.body().contains("{\"titre\":\"" + title + "\",\"auteurs\":[\"" + author + "\"],\"isbn\":\""
                + isbn + "\",\"idDansBiblio\":\"" + identifiant + "\"}"));

    }

}
