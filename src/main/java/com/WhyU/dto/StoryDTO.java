package com.WhyU.dto;

import com.WhyU.models.Attachment;
import com.WhyU.models.User;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryDTO implements Serializable {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User createdBy;
    private String createdByName;
    private Long createdByUserID;

    private User updatedBy;
    private String updatedByName;
    private Long updatedByUserID;

    private String head;
    private String description;

    private List<Long> framesIds;

    private Attachment preview;
    private String previewName;

    private Long previewAttachmentID;
}
