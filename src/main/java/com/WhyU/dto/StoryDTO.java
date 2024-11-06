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
    private long createdByUserID;

    private User updatedBy;
    private long updatedByUserID;

    private String head;
    private String description;

    private Attachment preview;
    private long previewAttachmentID;
}
