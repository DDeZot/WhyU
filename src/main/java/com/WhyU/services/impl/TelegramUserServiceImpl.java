package com.WhyU.services.impl;

import com.WhyU.dto.TelegramUserDTO;
import com.WhyU.models.TelegramUser;
import com.WhyU.models.User;
import com.WhyU.repositories.TelegramUserRepository;
import com.WhyU.services.TelegramUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;
    private final UserServiceImpl userService;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository, UserServiceImpl userService) {
        this.telegramUserRepository = telegramUserRepository;
        this.userService = userService;
    }

    public List<TelegramUser> findAllTelegramUsers() {
        return telegramUserRepository.findAll();
    }

    public TelegramUser findTelegramUserById(Long id) {
        return telegramUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь телеграмма с id " + " не найден"));
    }

    public TelegramUser findTelegramUserByTgId(Long tgID) {
        return telegramUserRepository.findByTelegramID(tgID)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь телеграмма с телеграмм-id " + " не найден"));
    }

    public TelegramUser createTelegramUser(TelegramUserDTO dto) {
        return telegramUserRepository.save(TelegramUser.builder()
                .user(dto.getUser() == null ? dto.getUser() : userService.findUserById(dto.getUserID()))
                .telegramID(dto.getTelegramID())
                .build());
    }

    public void deleteTelegramUserByTgId(Long tgID) {
        telegramUserRepository.deleteById(tgID);
    }

    public User getUserByTgId(Long tgID) {
        return findTelegramUserByTgId(tgID).getUser();
    }
}
