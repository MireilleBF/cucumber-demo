package fr.unice.polytech.biblio;

import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ph. Collet
 */
public class EmpruntLivreStepdefs { // implements En si vos scénarios sont écrits en anglais
    Bibliotheque biblio = new Bibliotheque();
    Etudiant etudiant;
    Livre livre;

    @Etantdonné("un etudiant de nom {string} et de noEtudiant {int}")
    public void etantDonneUnEtudiant(String nomEtudiant, Integer noEtudiant)  // besoin de refactorer int en Integer car utilisation de la généricité par Cucumber Java 8
    {
        etudiant = new Etudiant(biblio);
        etudiant.setNom(nomEtudiant);
        etudiant.setNoEtudiant(noEtudiant);
        biblio.addEtudiant(etudiant);
    }
    @Et("un livre de titre {string}")
    public void eUnLivre(String titreLivre) {
        Livre liv = new Livre(biblio);
        liv.setTitre(titreLivre);
        biblio.addLivre(liv);
    }
    @Quand("{string} demande son nombre d'emprunt")
    public void quandDemandeNbEmprunt(String nomEtudiant) {
        etudiant = biblio.getEtudiantByName(nomEtudiant);
    }

    @Alors("Il y a {int} dans son nombre d'emprunts")
    public void alors(Integer nbEmprunts)  {
        assertEquals(nbEmprunts.intValue(),etudiant.getNombreDEmprunt());
    }



    @Quand("{string} emprunte le livre {string}")
    public void quandEmprunte(String nomEtudiant, String titreLivre)  {
        etudiant = biblio.getEtudiantByName(nomEtudiant);
        livre = biblio.getLivreByTitle(titreLivre);
        etudiant.emprunte(livre);
    }
    @Et("Il y a le livre {string} dans un emprunt de la liste d'emprunts")
    public void etLivreDejaEmprunte(String titreLivre) {
        assertTrue(etudiant.getEmprunt().stream().
                anyMatch(emp -> emp.getLivreEmprunte().getTitre().equals(titreLivre)));
    }
    @Et("Le livre {string} est indisponible")
    public void etLivreDispo(String titreLivre)  {
        assertEquals(true, biblio.getLivreByTitle(titreLivre).getEmprunte());
    }



    @Quand("{string} rend le livre {string}")
    public void rendreLivre(String nomEtudiant, String titreLivre) {
        biblio.getEtudiantByName(nomEtudiant).rendre(titreLivre);
    }
    @Alors("Le livre {string} est disponible")
    public void le_livre_est_disponible(String titreLivre) {
        assertEquals(false, biblio.getLivreByTitle(titreLivre).getEmprunte());
    }


}
