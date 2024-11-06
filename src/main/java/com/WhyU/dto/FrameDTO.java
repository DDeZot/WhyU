package com.WhyU.dto;

import com.WhyU.models.Action;
import com.WhyU.models.Attachment;
import com.WhyU.models.Story;
import com.WhyU.models.enums.EndingType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameDTO implements Serializable {
    private String head;
    private String description;

    private Story story;
    private long storyID;

    private Attachment attachment;
    private long attachmentID;

    private List<Action> actions;
    private List<Action> gates;
    private boolean ending = false;
    private EndingType endingType = null;
}
