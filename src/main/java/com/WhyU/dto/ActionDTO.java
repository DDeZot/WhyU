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
    private String head;

    private Frame frame;
    private long frameID;

    private Frame consequence;
    private long consequenceFrameID;
}
