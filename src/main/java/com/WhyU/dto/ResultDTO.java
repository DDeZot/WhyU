package com.WhyU.dto;

import com.WhyU.models.Frame;
import com.WhyU.models.Story;
import com.WhyU.models.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

public class ResultDTO implements Serializable {
    private User user;
    private long userID;

    private Frame ending;
    private long endingFrameID;

    private Story story;
    private long storyID;
}
