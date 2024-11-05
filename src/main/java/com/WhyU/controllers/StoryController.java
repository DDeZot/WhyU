package com.WhyU.controllers;

import com.WhyU.dto.FrameDTO;
import com.WhyU.dto.StoryDTO;
import com.WhyU.models.Frame;
import com.WhyU.models.Story;
import com.WhyU.services.impl.AttachmentServiceImpl;
import com.WhyU.services.impl.StoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/stories")
public class StoryController {
    private final StoryServiceImpl storyService;

    @Autowired
    public StoryController(StoryServiceImpl storyService, AttachmentServiceImpl attachmentServiceImpl){
        this.storyService = storyService;
    }

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody StoryDTO dto){
            return ResponseEntity.ok().body(storyService.createStory(dto));
    }

    @PostMapping("/{id}/frames")
    public ResponseEntity<Frame> addFrame(@PathVariable Long id, @RequestBody FrameDTO dto) throws EntityNotFoundException {
        return ResponseEntity.ok().body(storyService.addFrame(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> findStoryById(@PathVariable Long id){
        return ResponseEntity.ok().body(storyService.findStoryById(id));
    }

    @GetMapping("/by_head/{head}")
    public ResponseEntity<Story> findStoryByHead(@PathVariable String head){
        return ResponseEntity.ok().body(storyService.findStoryByHead(head));
    }

    @GetMapping
    public List<Story> findAllStories(){
        return storyService.findAllStories();
    }

    @GetMapping("{id}/start")
    public ResponseEntity<Frame> getFirstFrame(@PathVariable Long id) throws EntityNotFoundException{
        return ResponseEntity.ok().body(storyService.getFirstFrame(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable Long id, @RequestBody StoryDTO dto){
            return ResponseEntity.ok().body(storyService.updateStory(id, dto));
    }

    @PatchMapping("/upload/{id}")
    public ResponseEntity<Story> addPreview(@PathVariable Long id, @RequestParam("file")MultipartFile file) throws IOException {
            return ResponseEntity.ok().body(storyService.uploadImageToStory(id, file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStory(@PathVariable Long id) {
        storyService.deleteStoryById(id);
        return ResponseEntity.ok().body("История с id " + id + " успешно удалена.");
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

