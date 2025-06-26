package com.WhyU.controllers;

import com.WhyU.models.Attachment;
import com.WhyU.services.impl.AttachmentServiceImpl;
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
@RequestMapping("api/attachments")
public class AttachmentController {
    private final AttachmentServiceImpl attachmentService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AttachmentController(AttachmentServiceImpl attachmentService, UserServiceImpl userServiceImpl){
        this.attachmentService = attachmentService;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Attachment> createAttachment(@RequestParam("file") MultipartFile file) throws IOException {
        Attachment attachment = attachmentService.createAttachment(file);
        attachment.setCreatedBy(userServiceImpl.findUserByUsername(getCurrentUser()));
        return ResponseEntity.ok(attachment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attachment> findAttachmentById(@PathVariable Long id) throws EntityNotFoundException{
        Attachment attachment = attachmentService.findAttachmentById(id);

        if (attachment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(attachment);
    }

    @GetMapping()
    public ResponseEntity<List<Attachment>> findAllAttachments(){
        return ResponseEntity.status(HttpStatus.OK).body(attachmentService.findAllAttachments());
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