package com.WhyU.dto;

import com.WhyU.models.User;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentDTO implements Serializable {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User createdBy;
    private String createdByName;
    private Long createdById;

    private User updatedBy;
    private String updatedByName;
    private Long updatedById;

    private byte[] bytes;

    private String path;
    private String fileName;
    private Long length;
}
