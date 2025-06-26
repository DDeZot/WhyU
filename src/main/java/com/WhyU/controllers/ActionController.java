package com.WhyU.controllers;

import com.WhyU.dto.ActionDTO;
import com.WhyU.dto.FrameDTO;
import com.WhyU.models.Action;
import com.WhyU.services.impl.ActionServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/actions")
public class ActionController {

    private final ActionServiceImpl actionService;

    public ActionController(ActionServiceImpl actionService) {
        this.actionService = actionService;
    }

    @PostMapping("/add_to_frame/{frameID}")
    public ResponseEntity<ActionDTO> createAction(@PathVariable Long frameID, @RequestBody ActionDTO dto) throws EntityNotFoundException {
        return ResponseEntity.ok().body(actionService.createAction(dto, frameID).getDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActionDTO> findActionById(@PathVariable Long id) throws  EntityNotFoundException {
        try {
            return ResponseEntity.ok().body(actionService.findActionById(id).getDTO());
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by_consequence_id/{consequenceID}")
    public ResponseEntity<List<ActionDTO>> findActionsByConsequenceId(@PathVariable Long consequenceID) throws EntityNotFoundException {
        return ResponseEntity.ok().body(actionService.findAllActionsByConsequenceId(consequenceID).stream().map(Action::getDTO).toList());
    }


    @GetMapping("/{id}/consequence")
    public ResponseEntity<FrameDTO> getConsequence(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(actionService.getConsequence(id).getDTO());
    }


    @PutMapping("/{id}")
    public ResponseEntity<ActionDTO> updateAction(@PathVariable Long id, @RequestBody ActionDTO dto) throws EntityNotFoundException {
        return ResponseEntity.ok().body(actionService.updateAction(id, dto).getDTO());
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
