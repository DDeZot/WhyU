package com.WhyU.models;

import com.WhyU.dto.FrameDTO;
import com.WhyU.models.enums.EndingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Entity
@Table(name = "frames")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Frame extends BasicModel {
    @Column(name = "head", columnDefinition = "varchar(100)")
    private String head;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "аttachment_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Attachment attachment;

    @OneToMany(mappedBy = "frame", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Action> actions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "consequence")
    private List<Action> gates;

    @Column(name = "ending")
    private Boolean ending = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "ending_type")
    private EndingType endingType = null;

    @Deprecated
    public void addAction(Action action){
        this.actions.add(action);
    }

    public FrameDTO getDTO() throws IOException {
        return FrameDTO.builder()
                .storyID(story.getId())
                .attachmentBytes(new FileInputStream(attachment.getPath()).readAllBytes())
                .attachmentID(attachment.getId())
                .ending(ending)
                .actionsIds((Long[]) actions.stream().map(BasicModel::getId).toArray())
                .gatesIds((Long[]) gates.stream().map(BasicModel::getId).toArray())
                .head(head)
                .description(description)
                .endingType(endingType)
                .build();
    }
}
