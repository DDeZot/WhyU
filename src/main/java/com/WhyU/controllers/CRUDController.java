package com.WhyU.controllers;

import com.WhyU.dto.AbstractDTO;
import com.WhyU.models.BasicModel;
import com.WhyU.services.CRUDService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;


public class CRUDController<E extends BasicModel, DTO extends AbstractDTO, S extends CRUDService<E, DTO>> {
    private final S service;

    public CRUDController(S service){
        this.service = service;
    }
    @PutMapping("/id")
    public ResponseEntity<E> create(DTO dto){
        try{
            return ResponseEntity.ok().body(service.create(dto));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
