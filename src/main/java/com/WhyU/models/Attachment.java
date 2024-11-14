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
        return AttachmentDTO.builder()
                .createdById(getCreatedBy().getId())
                .updatedById(getUpdatedBy().getId())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .fileName(fileName)
                .length(length)
                .bytes(new FileInputStream(path).readAllBytes())
                .build();
    }
}
