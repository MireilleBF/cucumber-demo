package fr.unice.polytech.biblio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.biblio.services.Bibliotheque;

import java.util.Collection;

/**
 * Ph. Collet
 * modifié par M. Blay-Fornarino
 *
 * Dans cette version, on ajoute la gestion des emprunts, surtout pour montrer
 * comment on peut gérer des collections d'objets dans un objet sérialisé.
 * et avoir plusieurs points de vue sur un même objet.
 */
public class Etudiant {

	@JsonProperty("name")
	private String nom;
	@JsonProperty("studentNumber")
	private int noEtudiant;


	public String getName() {
		return this.nom;
	}

	public void setName(String nom) {
		this.nom = nom;
	}

	@JsonProperty // pour Jackson
	public int getStudentNumber() {
		return this.noEtudiant;
	}

	public void setStudentNumber(int noEtudiant) {
		this.noEtudiant = noEtudiant;
	}

	public Collection<Emprunt> getEmprunts(Bibliotheque bibliotheque) {
		return bibliotheque.getEmpruntsByStudent(this);
	}

	//On considére qu'un seul exemplaire d'un livre peut etre emprunté par un étudiant
	public Emprunt getEmpruntFor(String livreTitre, Bibliotheque bibliotheque) {
		return getEmprunts(bibliotheque).stream().filter(e -> e.getLivreEmprunte().getTitre().equals(livreTitre)).findFirst().orElse(null);
	}

	@Override
	public String toString() {
		return "Etudiant{" +
				"nom='" + nom + '\'' +
				", noEtudiant=" + noEtudiant +
				'}';
	}
}