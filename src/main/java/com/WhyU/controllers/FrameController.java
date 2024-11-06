package com.WhyU.controllers;

import com.WhyU.dto.ActionDTO;
import com.WhyU.dto.FrameDTO;
import com.WhyU.models.Action;
import com.WhyU.models.Frame;
import com.WhyU.models.enums.EndingType;
import com.WhyU.services.impl.FrameServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/frames")
public class FrameController {

    private final FrameServiceImpl frameService;

    public FrameController(FrameServiceImpl frameService) {
        this.frameService = frameService;
    }

    @PostMapping("/by_story_id/{storyID}")
    public ResponseEntity<Frame> createFrame(@RequestBody FrameDTO dto, @PathVariable Long storyID) throws EntityNotFoundException {
        return ResponseEntity.ok().body(frameService.createFrame(dto, storyID));
    }

    @PostMapping("/by_story_head/{storyHead}")
    public ResponseEntity<Frame> createFrame(@RequestBody FrameDTO dto, @PathVariable String storyHead) throws EntityNotFoundException {
        return ResponseEntity.ok().body(frameService.createFrame(dto, storyHead));
    }

    @PostMapping("/{id}/actions")
    public ResponseEntity<Action> addAction(@PathVariable Long id, @RequestBody ActionDTO dto) throws  EntityNotFoundException{
        return ResponseEntity.ok().body(frameService.addAction(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Frame> findFrameById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(frameService.findFrameById(id));
    }

    @GetMapping("/{id}/actions")
    public List<Action> getAllActions(@PathVariable Long id) {
        return frameService.getAllActions(id);
    }

    @GetMapping
    public List<Frame> findAllFrames() {
        return frameService.findAllFrames();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Frame> updateFrame(@PathVariable Long id, @RequestBody FrameDTO dto) throws EntityNotFoundException {
        return ResponseEntity.ok().body(frameService.updateFrame(id, dto));
    }

    @PatchMapping("/upload/{id}")
    public ResponseEntity<Frame> uploadAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(frameService.uploadImageToFrame(id, file));
    }

    @PatchMapping("/{id}/ending_type")
    public ResponseEntity<Frame> setEndingType(@PathVariable Long id, EndingType endingType){
        return ResponseEntity.ok().body(frameService.setEnding(id, endingType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFrameById(@PathVariable Long id){
        frameService.deleteById(id);
        return ResponseEntity.ok().body("Кадр с id " + id + " успешно удален.");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleNotFound(IOException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
