package com.WhyU.controllers;

import com.WhyU.models.Attachment;
import com.WhyU.services.impl.AttachmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/attachments")
public class AttachmentController {
    private final AttachmentServiceImpl attachmentService;

    @Autowired
    public AttachmentController(AttachmentServiceImpl attachmentService){
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public ResponseEntity<String> createAttachment(@RequestParam("file") MultipartFile file)  {
        if (file.isEmpty())
            return ResponseEntity.badRequest().body("Выберите файл для загрузки.");

        try {
            attachmentService.createAttachment(file);
            return ResponseEntity.ok("Картинка загружена");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка загрузки картинки");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attachment> findAttachmentById(@PathVariable Long id) {
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
}