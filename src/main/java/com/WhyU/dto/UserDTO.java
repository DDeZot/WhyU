package com.WhyU.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements AbstractDTO {
    Long id;
    private String username;
    private LocalDate regDate;
}
