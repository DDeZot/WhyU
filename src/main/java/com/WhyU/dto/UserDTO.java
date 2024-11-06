package com.WhyU.dto;

import com.WhyU.models.Attachment;
import com.WhyU.models.Result;
import com.WhyU.models.Role;
import com.WhyU.models.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    private long profilePicAttachmentID;

    private List<Result> results;
    private List<Role> roles;
}
