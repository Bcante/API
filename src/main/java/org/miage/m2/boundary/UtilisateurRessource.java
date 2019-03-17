package org.miage.m2.boundary;

import java.util.List;

import org.miage.m2.entity.Tache;
import org.miage.m2.entity.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

public interface UtilisateurRessource extends CrudRepository<Utilisateur,String> {
 
	
}

