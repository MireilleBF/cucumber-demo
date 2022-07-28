package fr.unice.polytech.biblio;

        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Ph. Collet
 */
class LivreTest { // Just pour vérifier que JUnit 5 est bien configuré

    private Bibliotheque biblio;
    private Livre livre;

    @BeforeEach
    public void setUp() {
        biblio = new Bibliotheque();
        livre = new Livre(biblio);
    }

    @Test
    void EtudiantCreated() {

        assertEquals(false, livre.getEmprunte());
    }

}
