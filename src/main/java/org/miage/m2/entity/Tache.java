package org.miage.m2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Tache implements Serializable {

    @Id
    private String id;
    //@NotNull
    private String nom;
    //@NotNull
    private String responsable;
    //private Set<Utilisateur> participants;
    private String dateCreation;
    private String dateEcheance;
    private String etatcourant; // ENUM

    public Tache() {
    }
    
    public Tache(Tache t) {
    	this.dateCreation = LocalDate.now().toString();
    	this.nom = t.getNom();
		this.responsable = t.getResponsable();	
		this.etatcourant = "crée";
    }
    
	public Tache(String nom, String responsable, String etatcourant) {
		super();
		this.dateCreation = LocalDate.now().toString();
		this.nom = nom;
		this.responsable = responsable;
		this.etatcourant = "crée";
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
/*
	public Set<Utilisateur> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<Utilisateur> participants) {
		this.participants = participants;
	}*/

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
		return etatcourant;
	}

	public void setEtatCourant(String etatcourant) {
		this.etatcourant = etatcourant;
	}

    
    
    
    
}
