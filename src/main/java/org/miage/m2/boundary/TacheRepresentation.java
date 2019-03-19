package org.miage.m2.boundary;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.miage.m2.entity.Tache;
import org.miage.m2.entity.Utilisateur;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/taches", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Tache.class)
public class TacheRepresentation {

	    private final TacheRessource tr;
	    private final UtilisateurRessource ur;
	
	    public TacheRepresentation(TacheRessource tr, UtilisateurRessource ur) {
	        this.tr = tr;
	        this.ur =ur;
	    }

    // GET all by etat
    @GetMapping(params="etat")
    public ResponseEntity<?> getAlltachesByStatus(@RequestParam String etat) {
        Iterable<Tache> allTaches = tr.findByEtatcourant(etat);
        return new ResponseEntity<>(tacheToResource(allTaches), HttpStatus.OK);
    }
    // GET all
    @GetMapping()
    public ResponseEntity<?> getAlltaches() {
        Iterable<Tache> allTaches = tr.findAll();
        return new ResponseEntity<>(tacheToResource(allTaches), HttpStatus.OK);
    }

    // GET one
    @GetMapping(value = "/{tacheId}")
    public ResponseEntity<?> getTache(@PathVariable("tacheId") String id) {
        return Optional.ofNullable(tr.findById(id))
                .filter(Optional::isPresent)
                .map(i -> new ResponseEntity<>(tacheToResource(i.get(), true), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }    
    
    // POST
    @PostMapping
    public ResponseEntity<?> newTache(@RequestBody Tache tache) {
    	tache.setId(UUID.randomUUID().toString());
    	tache.setEtatCourant("encours");
        Tache saved = tr.save(tache);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(linkTo(TacheRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
    }
    // DELETE
    @DeleteMapping(value = "/{tacheId}")
    public ResponseEntity<?> deletetache(@PathVariable("tacheId") String id) {
        Optional<Tache> tache = tr.findById(id);
        if (tache.isPresent()) {
        	tache.get().setEtatCourant("achevée");
        	tr.save(tache.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // PUT
    @PutMapping(value = "/{tacheId}")
    public ResponseEntity<?> putTache (@PathVariable("tacheId") String id,@RequestBody Tache tache) {
    	    Optional<Tache> t = tr.findById(id);
    	    if (!t.isPresent())
    	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            t.get().setNom(tache.getNom());
            t.get().setResponsable(tache.getResponsable());
            tr.save(t.get());
            return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /*
     * Sur deux entités
     */
    
    //GET
    @GetMapping(value = "{tacheId}/participants")
    public ResponseEntity<?> getParticipantsFromTache(@PathVariable("tacheId") String id)
    {
    	return Optional.ofNullable(tr.findById(id))
                .filter(Optional::isPresent)
                .map(i -> new ResponseEntity<>(i.get().getParticipants(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping(value = "{tacheId}/participants/{utilisateurId}")
    public ResponseEntity<?> getParticipantFromTache(@PathVariable("tacheId") String idT, @PathVariable("utilisateurId") String idU)
    {
    	Optional<Tache> t = tr.findById(idT);
    	Set<Utilisateur> set = t.get().getParticipants();
    	for (Utilisateur u : set) {
    		if (u.getId().equals(idU)) {
    			return new ResponseEntity<>(u, HttpStatus.OK);
    		}
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // Post
    @PostMapping(value = "/{tacheId}/{utilisateurId}")
    public ResponseEntity<?> newTacheWithParticipants(@PathVariable("tacheId") String idT, @PathVariable("utilisateurId") String idU) {
    	Optional <Utilisateur> u = ur.findById(idU);
    	Optional <Tache> t = tr.findById(idT);
    	t.get().getParticipants().add(u.get());
    	tr.save(t.get());
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(linkTo(TacheRepresentation.class).slash(t.get().getId()).slash(u.get().getId()).toUri());
        return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
    }
    
    //Delete
    @DeleteMapping(value = "/{tacheId}/participants/{utilisateurId}")
    public ResponseEntity<?> removeUtilisateurFromTache(@PathVariable("tacheId") String id, @PathVariable("utilisateurId") String idU) {
        Optional<Tache> tache = tr.findById(id);
        if (tache.isPresent()) {
        	Set<Utilisateur> set = tache.get().getParticipants();
        	for (Utilisateur u : set) {
        		if (u.getId().equals(idU)) {
        			tache.get().getParticipants().remove(u);
        			tr.save(tache.get());
        		}
        	}
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    private Resources<Resource<Tache>> tacheToResource(Iterable<Tache> taches) {
        Link selfLink = linkTo(methodOn(TacheRepresentation.class).getAlltaches()).withSelfRel();
        List<Resource<Tache>> tacheRessources = new ArrayList();
        taches.forEach(tache
                -> tacheRessources.add(tacheToResource(tache, false)));
        return new Resources<>(tacheRessources, selfLink);
    }

    private Resource<Tache> tacheToResource(Tache tache, Boolean collection) {
        Link selfLink = linkTo(TacheRepresentation.class)
                .slash(tache.getId())
                .withSelfRel();
        if (collection) {
            Link collectionLink = linkTo(methodOn(TacheRepresentation.class).getAlltaches())
                    .withSelfRel();
            return new Resource<>(tache, selfLink, collectionLink);
        } else {
            return new Resource<>(tache, selfLink);
        }
    }
    
    
    
}
