package fr.unice.polytech.biblio.entities;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ph. Collet
 * Modifié par: M. Blay-Fornarino
 */
public class Livre {

    //@todo : le compteur sert à différencier les exemplaires d'un même livre
    //Il n'est pas à sa place ici...
	private String titre;
	private String[] auteurs;
	private String isbn;
	private String idDansBiblio;
	private boolean estEmprunte;

	private static final int DEFAULT_COMPTEUR = 0;

	static Logger logger = Logger.getLogger(Livre.class.getName());

	static {
		logger.setLevel(Level.OFF);
	}

	public Livre() {
		this(null, null, null,DEFAULT_COMPTEUR);
	}

	public Livre(String titre, String[] auteurs, String isbn){
		this(titre, auteurs, isbn, DEFAULT_COMPTEUR);
	}

	public Livre(String titre, String[] auteurs, String isbn, int compteur) {

		if (titre == null) {
			return;
		}
		logger.info("Création d'un livre avec titre = " + titre + " et compteur = " + compteur);
		this.titre = titre;
		if (auteurs == null) {
			this.auteurs = new String[0];
		} else
			this.auteurs = Arrays.copyOf(auteurs, auteurs.length);

		this.isbn = isbn;
		estEmprunte = false;
		// On crée un identifiant unique pour chaque livre
		//On ne garde que la première lettre de chaque mot du titre pour cela avec le compteur
		String[] mots = titre.split(" ");
		logger.log(Level.FINE, "titre " + titre + "=> mots = " + Arrays.toString(mots));
		StringBuilder titreCourt = new StringBuilder();
		for (String mot : mots) {
			titreCourt.append(mot.charAt(0));
		}
		idDansBiblio = titreCourt + "-" + compteur;
	}

	//On force la création d'un livre avec un titre et identifiant donné.
	//Cela facilitera les tests.
	public static Livre createLivre(String titre,  String identifiant) {
		Livre livre = new Livre(titre);
		livre.idDansBiblio = identifiant;
		return livre;
	}


	public Livre(String titreLivre) {
		this(titreLivre, new String[0], null, DEFAULT_COMPTEUR);
	}
	public Livre(String titreLivre, int compteur) {
		this(titreLivre, new String[0], null, compteur);
	}


	public String getTitre() {
		return this.titre;
	}

	/**
	 * 
	 * @param titre : titre du livre
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getIsbn() {
		return this.isbn;
	}

	/**
	 * 
	 * @param isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String[] getAuteurs() {
		return Arrays.copyOf(this.auteurs, this.auteurs.length);
	}

	/**
	 * 
	 * @param auteurs
	 */
	public void setAuteurs(String... auteurs) {
		if (auteurs == null) {
			this.auteurs = new String[0];
			return;
		}
		this.auteurs = Arrays.copyOf(auteurs, auteurs.length);
	}

	public boolean estEmprunte() {
		return this.estEmprunte;
	}

	/**
	 * 
	 * @param estEmprunte
	 */
	public void setEstEmprunte(boolean estEmprunte) {
		this.estEmprunte = estEmprunte;
	}

	public String getIdDansBiblio() {
		return this.idDansBiblio;
	}


	/**
	 * Deux livres sont égaux si ils ont le même identifiant,
	 * c'est à dire le même titre et le même compteur,
	 * donc qu'il s'agit du même exemplaire.
	 * @param o
	 * @return true si les deux livres sont égaux
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Livre livre = (Livre) o;
		return Objects.equals(idDansBiblio, livre.idDansBiblio);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(idDansBiblio);
	}

	@Override
	public String toString() {
		return "Livre{" +
				"'" + titre + '\'' +
				", de " + Arrays.toString(auteurs) +
				", isbn='" + isbn + '\'' +
				", idDansBiblio='" + idDansBiblio + '\'' +
				", estEmprunte=" + estEmprunte +
				'}';
	}
}