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
@RequestMapping(value = "/intervenants", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Tache.class)
public class IntervenantRepresentation {

    private final IntervenantRessource ir;

    public IntervenantRepresentation(IntervenantRessource ir) {
        this.ir = ir;
    }

    // GET all
    @GetMapping
    public ResponseEntity<?> getAllIntervenants() {
        Iterable<Tache> allItervenants = ir.findAll();
        return new ResponseEntity<>(intervenantToResource(allItervenants), HttpStatus.OK);
    }

    // GET one
    @GetMapping(value = "/{intervenantId}")
    public ResponseEntity<?> getIntervenant(@PathVariable("intervenantId") String id) {
        return Optional.ofNullable(ir.findById(id))
                .filter(Optional::isPresent)
                .map(i -> new ResponseEntity<>(intervenantToResource(i.get(), true), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST
    @PostMapping
    public ResponseEntity<?> newIntervenant(@RequestBody Tache intervenant) {
        intervenant.setId(UUID.randomUUID().toString());
        Tache saved = ir.save(intervenant);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(linkTo(IntervenantRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
    }

    // DELETE
    @DeleteMapping(value = "/{intervenantId}")
    public ResponseEntity<?> deleteIntervenant(@PathVariable("intervenantId") String id) {
        Optional<Tache> intervenant = ir.findById(id);
        if (intervenant.isPresent()) {
            ir.delete(intervenant.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Resources<Resource<Tache>> intervenantToResource(Iterable<Tache> intervenants) {
        Link selfLink = linkTo(methodOn(IntervenantRepresentation.class).getAllIntervenants()).withSelfRel();
        List<Resource<Tache>> intervenantRessources = new ArrayList();
        intervenants.forEach(intervenant
                -> intervenantRessources.add(intervenantToResource(intervenant, false)));
        return new Resources<>(intervenantRessources, selfLink);
    }

    private Resource<Tache> intervenantToResource(Tache intervenant, Boolean collection) {
        Link selfLink = linkTo(IntervenantRepresentation.class)
                .slash(intervenant.getId())
                .withSelfRel();
        if (collection) {
            Link collectionLink = linkTo(methodOn(IntervenantRepresentation.class).getAllIntervenants())
                    .withSelfRel();
            return new Resource<>(intervenant, selfLink, collectionLink);
        } else {
            return new Resource<>(intervenant, selfLink);
        }
    }
}
