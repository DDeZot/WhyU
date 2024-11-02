package com.WhyU.controllers;

import com.WhyU.dto.ActionDTO;
import com.WhyU.models.Action;
import com.WhyU.services.impl.ActionServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<Action> createAction(@PathVariable Long frameID, @RequestBody ActionDTO dto){
        try {
            return ResponseEntity.ok().body(actionService.createAction(dto, frameID));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Action> findActionById(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(actionService.findActionById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find_by_consequence_id/{consequenceID}")
    public ResponseEntity<Action> findActionByConsequenceId(@PathVariable Long consequenceID){
        try {
            return ResponseEntity.ok().body(actionService.findActionByConsequenceId(consequenceID));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find_by_frame_id/{frameID}")
    public List<Action> findAllActionsByFrameId(@PathVariable Long frameID){
        return actionService.findAllActionByFrameId(frameID);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Action> updateAction(@PathVariable Long id, @RequestBody ActionDTO dto){
        try {
            return ResponseEntity.ok().body(actionService.updateAction(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActionById(@PathVariable Long id){
        actionService.deleteActionById(id);
        return ResponseEntity.ok().body("Действие с id " + id + " успешно удалено.");
    }
}
