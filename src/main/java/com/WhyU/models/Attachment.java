package com.WhyU.models;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "attachments")//, indexes = @Index(columnList = "hash_code"))

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Attachment extends BasicPostingModel<User>{
    @Column(name = "bytes", nullable = false)
    @Lob
    private byte[] bytes;

    @Column(name = "file_name", nullable = false)
    @Pattern(regexp = ".*\\.(jpg|png)$", message = "File name must end with '.jpg' or '.png'")
    private String fileName;

    @Column(name = "hash_code", nullable = false)
    private Integer hashCode;
}
