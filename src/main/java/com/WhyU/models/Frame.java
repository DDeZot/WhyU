package com.WhyU.models;

import com.WhyU.dto.FrameDTO;
import com.WhyU.models.enums.FrameType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "frame_type")
    private FrameType frameType = FrameType.STANDARD;

    @Deprecated
    public void addAction(Action action){
        this.actions.add(action);
    }

    public FrameDTO getDTO() {
        byte[] bytes = null;

        if(attachment != null) {
            try (FileInputStream file = new FileInputStream(attachment.getPath())) {
                bytes = file.readAllBytes();
            } catch (IOException e) {
                bytes = null;
            }
        }

        return FrameDTO.builder()
                .id(id)
                .storyID(story.getId())
                .attachmentName(attachment == null ? null : attachment.getFileName())
                .attachmentID(attachment == null ? null : attachment.getId())
                .actionsIds(actions == null ? null : actions.stream().map(BasicModel::getId).collect(Collectors.toList()))
                .actionsHeads(actions == null ? null : actions.stream().map(Action::getHead).toList())
                .gatesIds(gates == null ? null : gates.stream().map(BasicModel::getId).toList())
                .head(head)
                .description(description)
                .frameType(frameType)
                .build();
    }
}
