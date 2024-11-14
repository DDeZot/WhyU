package com.WhyU.dto;

import com.WhyU.models.Attachment;
import com.WhyU.models.Result;
import com.WhyU.models.enums.Sex;
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
    private String username;
    private String password;
    private LocalDate regDate;
    private LocalDate birthDate;
    private Sex sex;
    protected String email;

    private Attachment profilePic;
    private Long profilePicAttachmentID;

    private List<Result> results;
    private String roles;
}
