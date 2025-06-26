package com.WhyU.dto;

import com.WhyU.models.Action;
import com.WhyU.models.Attachment;
import com.WhyU.models.Story;
import com.WhyU.models.enums.FrameType;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameDTO implements Serializable {
    private Long id;
    private String head;
    private String description;

    private Story story;
    private Long storyID;

    private Attachment attachment;
    private Long attachmentID;
    private String attachmentName;

    private List<Action> actions;
    private List<Long> actionsIds;
    private List<String> actionsHeads;
    private List<Action> gates;
    private List<Long> gatesIds;

    private FrameType frameType = null;
}
