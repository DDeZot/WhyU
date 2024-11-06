package com.WhyU.services;

import com.WhyU.dto.TelegramUserDTO;
import com.WhyU.models.TelegramUser;

import java.util.List;

public interface TelegramUserService {
    List<TelegramUser> findAllTelegramUsers();
    TelegramUser findTelegramUserById(Long id);
    TelegramUser findTelegramUserByTgId(Long tgID);
    TelegramUser createTelegramUser(TelegramUserDTO dto);
    TelegramUser deleteTelegramUserById(Long id);
}
