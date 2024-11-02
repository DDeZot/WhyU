package com.WhyU.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @JoinColumn(name = "prewiev_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Attachment preview;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Frame> frames;

    public void addFrame(Frame frame){
        this.frames.add(frame);
    }
}
