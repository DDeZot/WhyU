package com.WhyU.models;

import com.WhyU.dto.AttachmentDTO;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Entity
@Table(name = "attachments")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Attachment extends BasicPostingModel<User>{
    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "file_name", nullable = false)
    @Pattern(regexp = ".*\\.(jpg|png)$", message = "File name must end with '.jpg' or '.png'")
    private String fileName;

    @Column(name = "length", nullable = false)
    private Long length;

    public AttachmentDTO getDTO() throws IOException {
        byte[] bytes = null;

        try(FileInputStream file = new FileInputStream(path)){
            bytes = file.readAllBytes();
        } catch (IOException e){
            bytes = null;
        }
        return AttachmentDTO.builder()
                .id(id)
                .createdById(getCreatedBy() == null ? null : getCreatedBy().getId())
                .createdByName(getUpdatedBy() == null ? null : getCreatedBy().getUsername())
                .updatedById(getUpdatedBy() == null ? null : getUpdatedBy().getId())
                .updatedByName(getUpdatedBy() == null ? null : getUpdatedBy().getUsername())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .fileName(fileName)
                .length(length)
                .bytes(bytes)
                .build();
    }
}
