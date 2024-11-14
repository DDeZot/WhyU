package com.WhyU.models;

import com.WhyU.dto.UserDTO;
import com.WhyU.models.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class User extends BasicModel  {
    @Column(name = "username", columnDefinition = "varchar(60)", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;

    @Column(name = "reg_date")
    @CreationTimestamp
    private LocalDate regDate;

    @Column(name = "birth_date", nullable = true)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Email
    @Column(name = "email")
    protected String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_pic_id")
    private Attachment profilePic;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Result> results;

    private int getAge(){
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}
