package com.WhyU.controllers;

import com.WhyU.dto.FrameDTO;
import com.WhyU.models.Frame;
import com.WhyU.services.impl.FrameServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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

    @PostMapping("/add_to_story/{storyID}")
    public ResponseEntity<Frame> createFrame(@RequestBody FrameDTO dto, @PathVariable Long storyID) {
        return ResponseEntity.ok().body(frameService.createFrame(dto, storyID));
    }

    @PostMapping("/by_name/{storyName}")
    public ResponseEntity<Frame> createFrame(@RequestBody FrameDTO dto, @PathVariable String storyName) {
        return ResponseEntity.ok().body(frameService.createFrame(dto, storyName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Frame> findFrameById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(frameService.findFrameById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find_by_gate/{gateID}")
    public ResponseEntity<Frame> findFrameByGate(@PathVariable Long gateID) {
        try {
            return ResponseEntity.ok().body(frameService.findFrameByGateId(gateID));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Frame> findAllFrames() {
        return frameService.findAllFrames();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Frame> updateFrame(@PathVariable Long id, @RequestBody FrameDTO dto) {
        try {
            return ResponseEntity.ok().body(frameService.updateFrame(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/upload/{id}")
    public ResponseEntity<Frame> uploadAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok().body(frameService.uploadImageToFrame(id, file));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFrameById(@PathVariable Long id){
        frameService.deleteById(id);
        return ResponseEntity.ok().body("Кадр с id " + id + " успешно удален.");
    }
}
