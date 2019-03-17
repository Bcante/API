package org.miage.m2.boundary;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.miage.m2.entity.Tache;
import org.miage.m2.entity.Utilisateur;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/utilisateurs", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Utilisateur.class)
public class UtilisateurRepresentation {
	private final UtilisateurRessource ur;

    public UtilisateurRepresentation(UtilisateurRessource ur) {
        this.ur = ur;
    }
	// GET all
    @GetMapping()
    public ResponseEntity<?> getAllutilisateurs() {
        Iterable<Utilisateur> allUtilisateurs = ur.findAll();
        return new ResponseEntity<>(utilisateurToResource(allUtilisateurs), HttpStatus.OK);
    }
	
    private Resources<Resource<Utilisateur>> utilisateurToResource(Iterable<Utilisateur> utilisateurs) {
        Link selfLink = linkTo(methodOn(UtilisateurRepresentation.class).getAllutilisateurs()).withSelfRel();
        List<Resource<Utilisateur>> utilisateurRessources = new ArrayList();
        utilisateurs.forEach(utilisateur
                -> utilisateurRessources.add(utilisateurToResource(utilisateur, false)));
        return new Resources<>(utilisateurRessources, selfLink);
    }
    
	private Resource<Utilisateur> utilisateurToResource(Utilisateur utilisateur, Boolean collection) {
        Link selfLink = linkTo(UtilisateurRepresentation.class)
                .slash(utilisateur.getId())
                .withSelfRel();
        if (collection) {
            Link collectionLink = linkTo(methodOn(UtilisateurRepresentation.class).getAllutilisateurs())
                    .withSelfRel();
            return new Resource<>(utilisateur, selfLink, collectionLink);
        } else {
            return new Resource<>(utilisateur, selfLink);
        }
    }
    
    
}
