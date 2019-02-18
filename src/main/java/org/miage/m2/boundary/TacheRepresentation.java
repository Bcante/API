package org.miage.m2.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.miage.m2.entity.Tache;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/taches", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Tache.class)
public class TacheRepresentation {

    private final TacheRessource ir;

    public TacheRepresentation(TacheRessource ir) {
        this.ir = ir;
    }

    // GET all
    @GetMapping
    public ResponseEntity<?> getAlltaches() {
        Iterable<Tache> allTaches = ir.findAll();
        return new ResponseEntity<>(tacheToResource(allTaches), HttpStatus.OK);
    }

    // GET one
    @GetMapping(value = "/{tacheId}")
    public ResponseEntity<?> getTache(@PathVariable("tacheId") String id) {
        return Optional.ofNullable(ir.findById(id))
                .filter(Optional::isPresent)
                .map(i -> new ResponseEntity<>(tacheToResource(i.get(), true), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
    
    /*
    // POST
    @PostMapping
    public ResponseEntity<?> newtache(@RequestBody Tache tache) {
        tache.setId(UUID.randomUUID().toString());
        Tache saved = ir.save(tache);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(linkTo(TacheRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
    }

    // DELETE
    @DeleteMapping(value = "/{tacheId}")
    public ResponseEntity<?> deletetache(@PathVariable("tacheId") String id) {
        Optional<Tache> tache = ir.findById(id);
        if (tache.isPresent()) {
            ir.delete(tache.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    
}
