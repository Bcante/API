package org.miage.m2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tache implements Serializable {

    @Id
    private String id;
    //@NotNull
    private String nom;
    //@NotNull
    @OneToOne
    @JoinColumn(name ="idresponsable")
    private Utilisateur responsable;
    @OneToMany
    private Set<Utilisateur> participants;
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
		this.participants = new HashSet<>();
    }
    
	public Tache(String nom, Utilisateur responsable, String etatcourant) {
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

	public Utilisateur getResponsable() {
		return responsable;
	}

	public void setResponsable(Utilisateur responsable) {
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
		return etatcourant;
	}

	public void setEtatCourant(String etatcourant) {
		this.etatcourant = etatcourant;
	}

    
    
    
    
}
