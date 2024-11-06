package com.WhyU.services.impl;

import com.WhyU.models.Attachment;
import com.WhyU.repositories.AttachmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class AttachmentServiceImpl {
    private final String ATTACHMENT_DIRECTORY = "src/main/resources/uploads";
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }

    public List<Attachment> findAllAttachments(){
        return attachmentRepository.findAll();
    }

    public Attachment findAttachmentById(Long id){
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Вложение с ID " + id + " не найдено"));
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
        long fileLength = file.getSize();

        List<Attachment> existingAttachments = attachmentRepository.findAllByLength(file.getSize());

        if(!existingAttachments.isEmpty()) {
            for (Attachment a : existingAttachments) {
                try(FileInputStream f = new FileInputStream(a.getPath())) {
                    if (Arrays.equals(f.readAllBytes(), fileBytes)) {
                        return a;
                    }
                } catch (FileNotFoundException e) {
                    throw new IOException("У файла " + a.getFileName() + " в базе данных неправильно указан путь");
                }
            }
        }

        String directoryPath = ATTACHMENT_DIRECTORY + '/' + fileName;

        FileOutputStream newFile = new FileOutputStream(directoryPath);
        newFile.write(fileBytes);
        newFile.close();

        return attachmentRepository.save(Attachment.builder()
                .path(directoryPath)
                .fileName(fileName)
                .length(fileLength)
                .build());
    }

    public void deleteAttachmentById(Long id){
        attachmentRepository.deleteById(id);
    }
}
