package com.WhyU.controllers;

import com.WhyU.dto.StoryDTO;
import com.WhyU.models.Attachment;
import com.WhyU.models.Story;
import com.WhyU.services.impl.AttachmentServiceImpl;
import com.WhyU.services.impl.StoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/stories")
public class StoryController {
    private final StoryServiceImpl storyService;
    private final AttachmentServiceImpl attachmentServiceImpl;

    @Autowired
    public StoryController(StoryServiceImpl storyService, AttachmentServiceImpl attachmentServiceImpl){
        this.storyService = storyService;
        this.attachmentServiceImpl = attachmentServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody StoryDTO dto){
        try {
            return ResponseEntity.ok().body(storyService.createStory(dto));
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
    public ResponseEntity<String> deleteStory(@PathVariable Long id){

    }
}
