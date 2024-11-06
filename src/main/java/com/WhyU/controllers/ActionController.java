package com.WhyU.controllers;

import com.WhyU.dto.ActionDTO;
import com.WhyU.models.Action;
import com.WhyU.models.Frame;
import com.WhyU.services.impl.ActionServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/actions")
public class ActionController {

    private final ActionServiceImpl actionService;

    public ActionController(ActionServiceImpl actionService) {
        this.actionService = actionService;
    }

    @PostMapping("/add_to_frame/{frameID}")
    public ResponseEntity<Action> createAction(@PathVariable Long frameID, @RequestBody ActionDTO dto) throws EntityNotFoundException {
        return ResponseEntity.ok().body(actionService.createAction(dto, frameID));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Action> findActionById(@PathVariable Long id) throws  EntityNotFoundException {
        try {
            return ResponseEntity.ok().body(actionService.findActionById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by_consequence_id/{consequenceID}")
    public List<Action> findActionsByConsequenceId(@PathVariable Long consequenceID) throws EntityNotFoundException {
        return actionService.findAllActionsByConsequenceId(consequenceID);
    }


    @GetMapping("/{id}/consequence")
    public ResponseEntity<Frame> getConsequence(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(actionService.getConsequence(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Action> updateAction(@PathVariable Long id, @RequestBody ActionDTO dto) throws EntityNotFoundException {
        return ResponseEntity.ok().body(actionService.updateAction(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActionById(@PathVariable Long id) {
        actionService.deleteActionById(id);
        return ResponseEntity.ok().body("Действие с id " + id + " успешно удалено.");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
