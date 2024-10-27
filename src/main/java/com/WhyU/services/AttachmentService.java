package com.WhyU.services;

import com.WhyU.models.Attachment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface AttachmentService {
    public Attachment createAttachment(MultipartFile file) throws IOException;

    public List<Attachment> findAllAttachments();

    public Attachment findAttachmentById(Long id);

    public void deleteAttachmentById(Long id);
}
