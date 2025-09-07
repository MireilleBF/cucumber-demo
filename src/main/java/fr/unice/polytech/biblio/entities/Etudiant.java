package fr.unice.polytech.biblio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

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

	// Les emprunts courants
	// Ils ne doivent pas être sérialisés
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Collection<Emprunt> emprunts = new ArrayList<>();


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

	public Collection<Emprunt> getEmprunts() {
		return Collections.unmodifiableCollection(emprunts);
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public int getNombreDEmprunts() {
		return emprunts.size();
	}
	public void addEmprunt(Emprunt emprunt) {
		emprunts.add(emprunt);
	}

	public void removeEmprunt(Emprunt emprunt) {
		emprunts.remove(emprunt);
	}

	//On considére qu'un seul exemplaire d'un livre peut etre emprunté par un étudiant
	public Emprunt getEmpruntFor(String livreTitre){
		return emprunts.stream().filter(e -> e.getLivreEmprunte().getTitre().equals(livreTitre)).findFirst().orElse(null);
	}

	@Override
	public String toString() {
		return "Etudiant{" +
				"nom='" + nom + '\'' +
				", noEtudiant=" + noEtudiant +
				", Number of emprunts=" + emprunts.size() +
				'}';
	}
}