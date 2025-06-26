package com.WhyU.dto;

import com.WhyU.models.Attachment;
import com.WhyU.models.Result;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String name;
    private String password;
    private LocalDate regDate;
    private LocalDate birthDate;
    private String email;

    private Attachment profilePic;
    private Long profilePicAttachmentID;
    private String profilePicName;

    private List<Result> results;
    private List<Long> resultsIds;

    private String roles;
}
