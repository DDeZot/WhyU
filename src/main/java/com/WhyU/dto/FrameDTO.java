package com.WhyU.dto;

import com.WhyU.models.Action;
import com.WhyU.models.Attachment;
import com.WhyU.models.Story;
import com.WhyU.models.enums.EndingType;
import lombok.*;

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
    private Long storyID;

    private Attachment attachment;
    private Long attachmentID;
    private byte[] attachmentBytes;

    //private List<Action> actions;
    private Long[] actionsIds;
    //private List<Action> gates;
    private Long[] gatesIds;

    private Boolean ending = false;
    private EndingType endingType = null;
}
