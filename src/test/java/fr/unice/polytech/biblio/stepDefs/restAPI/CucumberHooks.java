package fr.unice.polytech.biblio.stepDefs.restAPI;

import fr.unice.polytech.biblio.apps.SimpleHttpServer4Library;
import fr.unice.polytech.biblio.apps.SimpleHttpServer4Scolarity;
import fr.unice.polytech.biblio.services.Bibliotheque;
import fr.unice.polytech.biblio.services.StudentRegistry;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CucumberHooks {

    static Logger logger = Logger.getLogger("Hooks");
    {
        logger.setLevel(Level.FINE);
    }

    @BeforeAll
    public static void setup() {
        int port4Library = SimpleHttpServer4Library.findFreePortFrom(TestContext.getPORT4LIBRARY());
        TestContext.setPORT4LIBRARY(port4Library);
        System.out.println("Port PORT4LIBRARY est " + port4Library);
        TestContext.setBASE_URL4LIBRARY("http://localhost:" + port4Library + "/api/books");

        int port4Scolarity = SimpleHttpServer4Scolarity.findFreePortFrom(TestContext.getPORT4SCOLARITY());
        TestContext.setPORT4SCOLARITY(port4Scolarity);
        System.out.println("Port PORT4SCOLARITY est " + port4Scolarity);
    }

    @Before
    public void beforeEach() throws IOException {
        TestContext.resetBeforeEachTest();

        logger.info("Je démarre les serveurs");

        TestContext.setBiblio(new Bibliotheque());
        TestContext.setStudentRegistry(new StudentRegistry());
        TestContext.setScolarity(SimpleHttpServer4Scolarity.startServer(TestContext.getPORT4SCOLARITY(), TestContext.getStudentRegistry()));
        TestContext.setLibrary(SimpleHttpServer4Library.startServer(TestContext.getPORT4LIBRARY(), TestContext.getBiblio(), TestContext.getStudentRegistry()));
    }

    @After
    public void teardown() {
        logger.info("J'arrete les serveurs");
        if (TestContext.getScolarity() != null) {
            TestContext.getScolarity().stop(0);
        }
        if (TestContext.getLibrary() != null) {
            TestContext.getLibrary().stop(0);
        }
    }
}
