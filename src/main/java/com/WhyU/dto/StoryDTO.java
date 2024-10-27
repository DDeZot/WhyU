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
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User createdBy;
    private User updatedBy;
    private String head;
    private String description;
    private Attachment preview;
}
