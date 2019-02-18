package org.miage.m2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Tache implements Serializable {

    @Id
    private String id;
    private String nom;
    private String responsable;
    private Set<Utilisateur> participants;
    private String dateCreation;
    private String dateEcheance;
    private String etatCourant; // ENUM

    public Tache() {
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Set<Utilisateur> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<Utilisateur> participants) {
		this.participants = participants;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(String dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public String getEtatCourant() {
		return etatCourant;
	}

	public void setEtatCourant(String etatCourant) {
		this.etatCourant = etatCourant;
	}

    
    
    
    
}
