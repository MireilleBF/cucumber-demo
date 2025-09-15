package fr.unice.polytech.biblio.stepDefs.restAPI;
import com.sun.net.httpserver.HttpServer;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.StudentRegistry;


public class TestContext {
    private static int PORT4LIBRARY = 8000;
    private static int PORT4SCOLARITY = 8001;
    private static String BASE_URL4LIBRARY;

    private static HttpServer scolarity;
    private static HttpServer library;
    private static StudentRegistry studentRegistry;
    private static Bibliotheque biblio;

    public static int getPORT4LIBRARY() { return PORT4LIBRARY; }
    public static void setPORT4LIBRARY(int port) { PORT4LIBRARY = port; }

    public static int getPORT4SCOLARITY() { return PORT4SCOLARITY; }
    public static void setPORT4SCOLARITY(int port) { PORT4SCOLARITY = port; }

    public static String getBASE_URL4LIBRARY() { return BASE_URL4LIBRARY; }
    public static void setBASE_URL4LIBRARY(String url) { BASE_URL4LIBRARY = url; }

    public static void resetBeforeEachTest() {
        scolarity = null;
        library = null;
        studentRegistry = null;
        biblio = null;
    }

    public static HttpServer getScolarity() { return scolarity; }
    public static void setScolarity(HttpServer s) { scolarity = s; }

    public static HttpServer getLibrary() { return library; }
    public static void setLibrary(HttpServer l) { library = l; }

    public static StudentRegistry getStudentRegistry() { return studentRegistry; }
    public static void setStudentRegistry(StudentRegistry s) { studentRegistry = s; }

    public static Bibliotheque getBiblio() { return biblio; }
    public static void setBiblio(Bibliotheque b) { biblio = b; }
}