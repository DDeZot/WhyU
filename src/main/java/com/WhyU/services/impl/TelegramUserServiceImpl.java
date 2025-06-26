package com.WhyU.services.impl;

import com.WhyU.dto.TelegramUserDTO;
import com.WhyU.dto.UserDTO;
import com.WhyU.models.TelegramUser;
import com.WhyU.models.User;
import com.WhyU.repositories.TelegramUserRepository;
import com.WhyU.repositories.UserRepository;
import com.WhyU.services.TelegramUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {
    private final TelegramUserRepository telegramUserRepository;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository, UserServiceImpl userService, UserRepository userRepository) {
        this.telegramUserRepository = telegramUserRepository;
        this.userService = userService;
        this.userRepository = userRepository;
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
                .user(dto.getUser() == null ? userService.findUserById(dto.getUserID()) : dto.getUser())
                .telegramID(dto.getTelegramID())
                .build());
    }

    public TelegramUser changeUsername(Long tgID, UserDTO dto) {
        TelegramUser telegramUser = findTelegramUserById(tgID);
        User user = telegramUser.getUser();
        if(user == null)
            return null;
        user.setUsername(dto.getUsername());

        userRepository.save(user);
        return telegramUser;
    }

    public void deleteTelegramUserByTgId(Long tgID) {
        telegramUserRepository.deleteById(tgID);
    }

    public User getUserByTgId(Long tgID) {
        return findTelegramUserByTgId(tgID).getUser();
    }
}
