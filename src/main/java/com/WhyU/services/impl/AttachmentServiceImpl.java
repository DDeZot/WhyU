package com.WhyU.services.impl;

import com.WhyU.models.Attachment;
import com.WhyU.repositories.AttachmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class AttachmentServiceImpl {
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment createAttachment(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Файл пустой!");
        }

        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String lowerCaseFileName = fileName.toLowerCase();
        if (!(lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".png"))) {
            throw new IOException("Неверный формат изображения");
        }

        byte[] fileBytes = file.getBytes();
        int hashCode = Arrays.hashCode(fileBytes);
        Attachment existingAttachment = attachmentRepository.findByHashCode(hashCode)
                .orElse(null);

        if (existingAttachment != null && Arrays.equals(existingAttachment.getBytes(), fileBytes)) {
            return existingAttachment;
        }

        return attachmentRepository.save(Attachment.builder()
                .bytes(fileBytes)
                .fileName(fileName)
                .hashCode(hashCode)
                .build());
    }

    public List<Attachment> findAllAttachments(){
        return attachmentRepository.findAll();
    }

    public Attachment findAttachmentById(Long id){
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Вложение с ID " + id + " не найдено"));
    }

    public void deleteAttachmentById(Long id){
        attachmentRepository.deleteById(id);
    }
}
