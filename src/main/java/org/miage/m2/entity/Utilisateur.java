package org.miage.m2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Utilisateur {

	@Id
	private String id;
	private String nom;
	
	public Utilisateur() {
		
	}

	public Utilisateur(String nom) {
		this.nom = nom;
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
	
	
	
}
