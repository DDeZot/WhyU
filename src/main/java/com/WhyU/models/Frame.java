package com.WhyU.models;

import com.WhyU.models.enums.EndingType;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gate_id")
    private Action gate;

    @Column(name = "ending")
    private Boolean ending = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "ending_type")
    private EndingType endingType = null;

    @Deprecated
    public void addAction(Action action){
        this.actions.add(action);
    }
}
