package com.WhyU.models;

import com.WhyU.dto.TelegramUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "telegram_users")

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class TelegramUser {
    @Id
    @Column(name = "tg_id")
    Long telegramID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    public TelegramUserDTO getDTO(){
        return TelegramUserDTO.builder()
                .userID(user.getId())
                .telegramID(telegramID)
                .build();
    }
}
