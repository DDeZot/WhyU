package com.WhyU.models;

import com.WhyU.dto.ResultDTO;
import com.WhyU.models.enums.EndingType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "results")

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class Result extends BasicModel {
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ending_id")
    private Frame ending;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    @Setter
    private Story story;

    @Enumerated
    @Column(name = "ending_type")
    EndingType endingType;

    public void setEnding(Frame endingFrame){
        this.ending = endingFrame;
        this.endingType = endingFrame.getEndingType();
    }

    public ResultDTO getDto(){
        return ResultDTO.builder()
                .userID(user.getId())
                .endingFrameID(ending.getId())
                .storyID(story.getId())
                .endingFrameDescription(ending.getDescription())
                .endingType(endingType)
                .build();
    }
}
