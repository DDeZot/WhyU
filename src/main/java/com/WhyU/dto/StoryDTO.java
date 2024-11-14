package com.WhyU.dto;

import com.WhyU.models.Attachment;
import com.WhyU.models.User;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryDTO implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User createdBy;
    private Long createdByUserID;

    private User updatedBy;
    private Long updatedByUserID;

    private String head;
    private String description;

    private Long[] framesIds;

    private Attachment preview;
    private byte[] previewBytes;
    private Long previewAttachmentID;
}
