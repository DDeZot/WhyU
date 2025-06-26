package com.WhyU.models;

import com.WhyU.dto.StoryDTO;
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
import java.util.stream.Collectors;

@Entity
@Table(name = "stories")

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Story extends BasicPostingModel<User> {
    @Column(name = "head", unique = true)
    private String head;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prewiev_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Attachment preview;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Frame> frames;

    @Deprecated
    public void addFrame(Frame frame){
        this.frames.add(frame);
    }

    public StoryDTO getDTO() {
        byte[] bytes = null;

        if (preview != null){
            try (FileInputStream file = new FileInputStream(preview.getPath())) {
                bytes = file.readAllBytes();
            } catch (IOException e) {
                bytes = null;
            }
        }

        return StoryDTO.builder()
                .id(id)
                .createdByUserID(getCreatedBy() == null ? null : getCreatedBy().getId())
                .updatedByUserID(getUpdatedBy() == null ? null : getUpdatedBy().getId())
                .createdByName(getCreatedBy() == null ? null : getCreatedBy().getUsername())
                .updatedByName(getUpdatedBy() == null ? null : getUpdatedBy().getUsername())
                .previewAttachmentID(preview == null ? null : preview.getId())
                .previewName(preview == null ? null : preview.getFileName())
                .framesIds(frames == null ? null : frames.stream().map(BasicModel::getId).collect(Collectors.toList()))
                .head(head)
                .description(description)
                .build();
    }
}
