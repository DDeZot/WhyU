package com.WhyU.controllers;

import com.WhyU.dto.TelegramUserDTO;
import com.WhyU.dto.UserDTO;
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
    public ResponseEntity<List<TelegramUserDTO>> findAllTelegramUsers(){
        return ResponseEntity.ok().body(telegramUserService.findAllTelegramUsers().stream().map(TelegramUser::getDTO).toList());
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<UserDTO> getUserByTgId(@PathVariable Long id) throws EntityNotFoundException{
        return ResponseEntity.ok().body(telegramUserService.getUserByTgId(id).getDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TelegramUserDTO> findTelegramUserByTgId(@PathVariable Long id) throws EntityNotFoundException{
        return ResponseEntity.ok().body(telegramUserService.findTelegramUserByTgId(id).getDTO());
    }

    @PostMapping
    public ResponseEntity<TelegramUserDTO> createTelegramUser(@RequestBody TelegramUserDTO dto) throws  EntityNotFoundException{
        return ResponseEntity.ok().body(telegramUserService.createTelegramUser(dto).getDTO());
    }

    @PatchMapping("{tgID}/username")
    public ResponseEntity<TelegramUserDTO> changeUsername(@PathVariable Long tgID, @RequestBody UserDTO user) throws EntityNotFoundException{
        return ResponseEntity.ok().body(telegramUserService.changeUsername(tgID, user).getDTO());
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
