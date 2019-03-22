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
import java.util.UUID;

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
    private LocalDate dateCreation;
    private LocalDate dateEcheance;
    private String etatcourant; // ENUM
    private String token;

    public Tache() {
    }
    
    public Tache(Tache t) {
    	this.dateCreation = LocalDate.now();
    	this.nom = t.getNom();
		this.responsable = t.getResponsable();	
		this.etatcourant = Etat.crée.toString();
		this.participants = new HashSet<>();
    }
    
	public Tache(String nom, Utilisateur responsable, String etatcourant) {
		super();
		this.dateCreation = LocalDate.now();
		this.nom = nom;
		this.responsable = responsable;
		this.etatcourant = Etat.crée.toString();
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

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public String getEtatCourant() {
		return etatcourant;
	}

	public void setEtatCourant(String etat) {
		this.etatcourant = etat;
	}

	public String getToken() {
		return token;
	}

	public String generateToken() {
		this.token = UUID.randomUUID().toString();
		return this.token;
	}
    
}
