package com.WhyU.controllers;

import com.WhyU.dto.StoryDTO;
import com.WhyU.models.Story;
import com.WhyU.services.impl.AttachmentServiceImpl;
import com.WhyU.services.impl.StoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
        try {
            return ResponseEntity.ok().body(storyService.createStory(dto));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> findStoryById(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(storyService.findStoryById(id));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find_by_head/{head}")
    public ResponseEntity<Story> findStoryByHead(@PathVariable String head){
        try {
            return ResponseEntity.ok().body(storyService.findStoryByHead(head));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Story> findAllStories(){
        return storyService.findAllStories();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Story> updateStory(@PathVariable Long id, @RequestBody StoryDTO dto){
        try{
            return ResponseEntity.ok().body(storyService.updateStory(id, dto));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/upload/{id}")
    public ResponseEntity<Story> addPreview(@PathVariable Long id, @RequestParam("file")MultipartFile file) throws IOException {
        try {
            return ResponseEntity.ok().body(storyService.uploadImageToStory(id, file));
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStory(@PathVariable Long id) {
        storyService.deleteStoryById(id);
        return ResponseEntity.ok().body("История с id " + id + " успешно удалена.");
    }
}

