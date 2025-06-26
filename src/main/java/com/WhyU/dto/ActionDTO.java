package com.WhyU.dto;

import com.WhyU.models.Frame;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionDTO implements Serializable {
    private Long id;
    private String head;

    private Frame frame;
    private Long frameID;

    private Frame consequence;
    private Long consequenceFrameID;
}
