package org.miage.m2.boundary;

import org.miage.m2.entity.Tache;
import org.springframework.data.repository.CrudRepository;

public interface TacheRessource extends CrudRepository<Tache,String> {
    
}

