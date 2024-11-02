package com.WhyU.dto;

import com.WhyU.models.Action;
import com.WhyU.models.Attachment;
import com.WhyU.models.Story;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameDTO implements AbstractDTO {
    private Long id;
    private String head;
    private String description;
    private Story story;
    private Attachment attachment;
    private Action gate;
    private List<Action> actions;
}
