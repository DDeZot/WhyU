package com.WhyU.dto;

import com.WhyU.models.Frame;
import com.WhyU.models.Story;
import com.WhyU.models.User;
import com.WhyU.models.enums.FrameType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDTO implements Serializable {
    private Long id;
    private User user;
    private Long userID;

    private Frame ending;
    private Long endingFrameID;

    private Story story;
    private Long storyID;

    private String endingFrameDescription;
    private String storyHead;

    private FrameType frameType;
}
