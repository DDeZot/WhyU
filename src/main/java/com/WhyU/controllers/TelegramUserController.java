package com.WhyU.controllers;

import com.WhyU.dto.TelegramUserDTO;
import com.WhyU.models.TelegramUser;
import com.WhyU.models.User;
import com.WhyU.services.impl.TelegramUserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/telegram_users")
public class TelegramUserController {
    private final TelegramUserServiceImpl telegramUserService;

    @Autowired
    public TelegramUserController(TelegramUserServiceImpl telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @GetMapping()
    public List<TelegramUser> findAllTelegramUsers(){
        return telegramUserService.findAllTelegramUsers();
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<User> getUserByTgId(@PathVariable Long id){
        return ResponseEntity.ok().body(telegramUserService.getUserByTgId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelegramUser> findTelegramUserByTgId(@PathVariable Long id){
        return ResponseEntity.ok().body(telegramUserService.findTelegramUserByTgId(id));
    }

    @PostMapping
    private ResponseEntity<TelegramUser> createTelegramUser(@RequestBody TelegramUserDTO dto){
        return ResponseEntity.ok().body(telegramUserService.createTelegramUser(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTelegramUserById(@PathVariable Long id) {
        telegramUserService.deleteTelegramUserByTgId(id);
        return ResponseEntity.ok().body("Пользователь телеграмма с id " + id + " успешно удален");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
