package fr.unice.polytech.biblio.stepDefs.restAPI;

import fr.unice.polytech.biblio.api.HttpUtils;
import fr.unice.polytech.biblio.api.JaxsonUtils;
import fr.unice.polytech.biblio.api.dtos.StudentDTO;
import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.entities.Livre;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.StudentRegistry;
import fr.unice.polytech.biblio.services.exceptions.ResourceAlreadyExistsException;
import fr.unice.polytech.biblio.services.exceptions.ResourceNotFoundException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class APIStepdefs {

    static Logger logger = Logger.getLogger("APIStepdefs");
    {
        logger.setLevel(Level.OFF);
    }

    private static String BASE_URL4LIBRARY;
    StudentRegistry studentRegistry;
    Bibliotheque biblio;

    @Given("the API test servers are configured and started")
    public void theTestServersAreConfiguredAndStarted() {
        BASE_URL4LIBRARY = TestContext.getBASE_URL4LIBRARY();
        studentRegistry = TestContext.getStudentRegistry();
        biblio = TestContext.getBiblio();
    }

    @Given("{int} books are at least already registered in the library")
    public void n_BooksAreAlreadyRegisteredInTheLibrary(Integer numberOfBooks) {
        // By default the server is started with at least 3 books
        // we check that the books are present only to be sure
        List<Livre> livres = biblio.getLivres();
        registerBooks(livres, numberOfBooks);
    }

    private void registerBooks(List<Livre> livres, Integer numberOfBooks) {
        if (numberOfBooks > livres.size()) {
            for (int i = livres.size(); i < numberOfBooks; i++) {
                Livre l = new Livre("Title" + i, new String[] { "author" + i }, "", i);
                biblio.addLivre(l);
            }
        }
    }

    @Given("a book of title {string} with id {string} has been registered and is available")
    public void a_book_of_title_with_id_nlmg_has_been_registered_and_is_available(String title, String bookId) {
        Livre l = getOrCreateAndRegisterABook(title, bookId);
        l.setEstEmprunte(false);
    }

    @Given("a book of title {string} with id {string} has been registered and is not available")
    public void a_book_of_title_with_id_has_been_registered_and_is_not_available(String title, String bookId) {
        Livre l = getOrCreateAndRegisterABook(title, bookId);
        rendreIndisponible(l);
    }

    @Given("a book of title {string} with id {string} has not been registered")
    public void a_book_of_title_with_id_has_not_been_registered(String title, String id) {
        try {
            biblio.getLivreParBiblioId(id);
        } catch (ResourceNotFoundException e) {
            return;
        }
        throw new IllegalStateException("Book with id " + id + " already exists");
    }

    private Livre getOrCreateAndRegisterABook(String title, String bookId) {
        Livre l;
        try {
            l = biblio.getLivreParBiblioId(bookId);
        } catch (ResourceNotFoundException e) {
            l = Livre.createLivre(title, bookId);
        }
        biblio.addLivre(l);
        return l;
    }

    private void rendreIndisponible(Livre l) {
        l.setEstEmprunte(true);
    }

    /** Given statements about the students */

    @Given("{int} students are at least already registered by the scolarship service")
    public void n_students_are_already_registered_by_the_scolarship_service(Integer numberOfStudents) {
        // By default the server is started with 2 students
        // we check that the students are present only to be sure
        var students = studentRegistry.findAll();
        logger.log(Level.FINE, "Students : {0}", students);
        assertTrue(numberOfStudents <= students.size());
    }

    int previousNumberOfLoans;

    @Given("a registered student named {string} with student number {int}")
    public void a_student_named_and_with_student_number_was_registered(String name, Integer id) throws ResourceAlreadyExistsException {
        var student = a_student_has_been_registered(name, id);
        previousNumberOfLoans = student.getEmprunts(biblio).size();

    }

    private Etudiant a_student_has_been_registered(String name, Integer ident) throws ResourceAlreadyExistsException {
        Optional<Etudiant> student = studentRegistry.findByNumber(ident);
        if (student.isPresent()) {
            return student.get();
        } else {
            studentRegistry.addStudent(name, ident);
        }
        return studentRegistry.findByNumber(ident).get();
    }

    @Given("a student of name {string} and with student id {int} has not been registered")
    public void a_student_of_name_and_with_student_id_has_not_been_registered(String name, Integer ident) {
        studentRegistry.findByNumber(ident).ifPresent(student -> {
            try {
                studentRegistry.removeStudent(ident);
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    HttpResponse<String> response;

    @When("the librarian adds a book with the title {string}, the author {string}, isbn {string}")
    public void librarian_add_a_book_with_the_title_the_author_isbn(String title, String authorName, String isbn)
            throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var uri = URI.create(BASE_URL4LIBRARY);

        Livre livre = new Livre(title, new String[] { authorName }, isbn);
        String newBook = JaxsonUtils.toJson(livre);
        response = client.send(
                HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(newBook))
                        .uri(uri)
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpUtils.CREATED, response.statusCode());
        assertEquals(HttpUtils.TEXT_PLAIN, response.headers().firstValue(HttpUtils.CONTENT_TYPE).orElse(""));
        logger.info(response.body());

        assertEquals("Book created", response.body());
    }

    @When("a user asks to get the list of registered books")
    public void user_ask_to_get_the_list_of_registered_books() throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var uri = URI.create(BASE_URL4LIBRARY);
        response = client.send(
                HttpRequest.newBuilder()
                        .GET()
                        .uri(uri)
                        .build(),
                HttpResponse.BodyHandlers.ofString());
    }

    @When("the student with id {int} books the book with id {string}")
    public void the_student_books_the_book_with_id(Integer studentId, String bookId)
            throws IOException, InterruptedException {
        /*
         * Version sans passer par http
         * Etudiant student = studentRegistry.findByNumber(studentId).get();
         * Livre book = biblio.getLivrebyId(bookId);
         * biblio.emprunte(student,book);
         */
        var client = HttpClient.newHttpClient();
        var uri = URI.create(BASE_URL4LIBRARY + "/" + bookId + "/borrow");

        StudentDTO dto = new StudentDTO(studentId);
        String jsonDTO = JaxsonUtils.toJson(dto);
        logger.log(Level.FINE, "Json before : {0}", jsonDTO);
        response = client.send(
                HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(jsonDTO))
                        .uri(uri)
                        .build(),
                HttpResponse.BodyHandlers.ofString());
    }

    /***** Then statements *****/

    @Then("the server should return a success status")
    public void the_server_should_return_a_success_status() {
        int statuscode = response.statusCode();
        assertTrue(statuscode == HttpUtils.OK || statuscode == HttpUtils.CREATED);
    }

    @Then("the server should return a failure status")
    public void the_server_should_return_a_failure_status() {
        int statuscode = response.statusCode();
        assertTrue(statuscode == HttpUtils.BAD_REQUEST ||
                statuscode == HttpUtils.RESOURCE_NOT_FOUND ||
                statuscode == HttpUtils.CONFLICT ||
                statuscode == HttpUtils.UNPROCESSABLE_ENTITY);
    }

    @Then("a book of title {string} is registered in the library")
    public void a_book_of_title_is_registered_in_the_library(String title) {
        // We check that the book is in the library by searching for it and not using
        // http request
        List<Livre> livres = biblio.getLivresByTitle(title);
        assertFalse(livres.isEmpty());
    }

    @Then("the library contains {int} books")
    public void the_library_contains_books(Integer numberOfBooks) {
        // We check that the book is in the library by searching for it and not using
        // http request
        List<Livre> livres = biblio.getLivres();
        assertEquals(numberOfBooks, livres.size());
    }

    @Then("the requested list is returned in json format")
    public void the_requested_list_is_returned_in_json_format() {
        assertEquals(HttpUtils.OK, response.statusCode());
        assertEquals(HttpUtils.APPLICATION_JSON, response.headers().firstValue(HttpUtils.CONTENT_TYPE).orElse(""));
    }

    @Then("the list contains at least {int} books")
    public void the_list_contains_at_least_books(Integer numberOfBooks) {
        Livre[] books = JaxsonUtils.fromJson(response.body(), Livre[].class);
        assertTrue(numberOfBooks < books.length);
    }

    @Then("the library contains at least {int} books")
    public void the_library_contains_at_least_books(Integer int1) {
        List<Livre> livres = biblio.getLivres();
        assertTrue(int1 <= livres.size());
    }

    @Then("there is one more loan for the student with the student number {int}")
    public void there_is_one_more_loan_for_the_student_with_the_student_number(Integer id) {
        var student = studentRegistry.findByNumber(id).get();
        assertEquals(previousNumberOfLoans + 1, student.getEmprunts(biblio).size());
    }

    @Then("the number of loans has not changed for the student with the student number {int}")
    public void no_more_loan_for_the_student_with_the_student_number(Integer id) {
        var student = studentRegistry.findByNumber(id).get();
        assertEquals(previousNumberOfLoans, student.getEmprunts(biblio).size());

    }

    @Then("the book with id {string} is no longer available")
    public void the_book_with_id_is_no_longer_available(String bookId) throws ResourceNotFoundException {
        var book = biblio.getLivreParBiblioId(bookId);
        assertTrue(book.estEmprunte());
    }

    @Then("the book with id {string} is still available")
    public void the_book_with_id_is_still_available(String bookId) throws ResourceNotFoundException {
        var book = biblio.getLivreParBiblioId(bookId);
        assertFalse(book.estEmprunte());
    }

    @Then("the server should return a message {string}")
    public void the_server_should_return_a_message(String message) {
        assertEquals(message, response.body());
    }

}
