package com.WhyU.services;

import com.WhyU.models.Attachment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface AttachmentService {
    Attachment createAttachment(MultipartFile file) throws IOException;
    List<Attachment> findAllAttachments();
    Attachment findAttachmentById(Long id);
    void deleteAttachmentById(Long id);
}
