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

    public StoryDTO getDTO() throws IOException {
        return StoryDTO.builder()
                .createdByUserID(getCreatedBy().getId())
                .updatedByUserID(getUpdatedBy().getId())
                .previewBytes(new FileInputStream(preview.getPath()).readAllBytes())
                .previewAttachmentID(preview.getId())
                .framesIds((Long[]) frames.stream().map(BasicModel::getId).toArray())
                .head(head)
                .description(description)
                .build();
    }
}
