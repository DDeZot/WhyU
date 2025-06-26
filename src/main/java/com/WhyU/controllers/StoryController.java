package com.WhyU.controllers;

import com.WhyU.dto.FrameDTO;
import com.WhyU.dto.StoryDTO;
import com.WhyU.models.Story;
import com.WhyU.services.impl.AttachmentServiceImpl;
import com.WhyU.services.impl.StoryServiceImpl;
import com.WhyU.services.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/stories")
public class StoryController {
    private final StoryServiceImpl storyService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public StoryController(StoryServiceImpl storyService, AttachmentServiceImpl attachmentServiceImpl, UserServiceImpl userServiceImpl){
        this.storyService = storyService;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<StoryDTO> createStory(@RequestBody StoryDTO dto){
        dto.setCreatedBy(userServiceImpl.findUserByUsername(getCurrentUser()));
        return ResponseEntity.ok().body(storyService.createStory(dto).getDTO());
    }

    @PostMapping("/{id}/frames")
    public ResponseEntity<FrameDTO> addFrame(@PathVariable Long id, @RequestBody FrameDTO dto) throws EntityNotFoundException {
        return ResponseEntity.ok().body(storyService.addFrame(id, dto).getDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> findStoryById(@PathVariable Long id) throws EntityNotFoundException{
        return ResponseEntity.ok().body(storyService.findStoryById(id).getDTO());
    }

    @GetMapping("/by_head/{head}")
    public ResponseEntity<StoryDTO> findStoryByHead(@PathVariable String head) throws EntityNotFoundException{
        return ResponseEntity.ok().body(storyService.findStoryByHead(head).getDTO());
    }

    @GetMapping("/latest")
    public ResponseEntity<StoryDTO> findLatestStory() throws EntityNotFoundException{
        return ResponseEntity.ok().body(storyService.findLatestStory().getDTO());
    }
    @GetMapping
    public ResponseEntity<List<StoryDTO>> findAllStories(){
        return ResponseEntity.ok().body(storyService.findAllStories().stream().map(Story::getDTO).toList());
    }

    @GetMapping("{id}/start")
    public ResponseEntity<FrameDTO> getFirstFrame(@PathVariable Long id) throws EntityNotFoundException{
        return ResponseEntity.ok().body(storyService.getFirstFrame(id).getDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryDTO> updateStory(@PathVariable Long id, @RequestBody StoryDTO dto){
        dto.setUpdatedBy(userServiceImpl.findUserByUsername(getCurrentUser()));
        return ResponseEntity.ok().body(storyService.updateStory(id, dto).getDTO());
    }

    @PatchMapping("/upload/{id}")
    public ResponseEntity<StoryDTO> addPreview(@PathVariable Long id, @RequestParam("file")MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(storyService.uploadImageToStory(id, file).getDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStory(@PathVariable Long id) {
        storyService.deleteStoryById(id);
        return ResponseEntity.ok().body("История с id " + id + " успешно удалена.");
    }

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : null;
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

