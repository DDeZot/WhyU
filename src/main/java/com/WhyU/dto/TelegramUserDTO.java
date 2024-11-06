package com.WhyU.dto;

import com.WhyU.models.User;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelegramUserDTO implements Serializable {
    private Long telegramID;
    private Long userID;
    private User user;
}
