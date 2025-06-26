package com.WhyU.controllers;

import com.WhyU.dto.UserDTO;
import com.WhyU.models.Attachment;
import com.WhyU.models.Frame;
import com.WhyU.models.User;
import com.WhyU.services.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    @GetMapping("/by_name/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) throws UsernameNotFoundException {
        return ResponseEntity.ok().body(userService.findUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO dto){
        return ResponseEntity.ok().body(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) throws EntityNotFoundException {
        return ResponseEntity.ok().body(userService.updateUser(id, dto));
    }

    @PatchMapping("/{id}/upload")
    public ResponseEntity<User> uploadProfilePic(@PathVariable Long id, @RequestParam("file")MultipartFile image) throws IOException, EntityNotFoundException {
        return ResponseEntity.ok().body(userService.uploadProfilePic(id, image));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<User> changePassword(@PathVariable Long id, String password){
        return ResponseEntity.ok().body(userService.changePassword(id, password));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().body("Пользователь с id " + id + " успешно удален.");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleNotFound(IOException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleNotFound(UsernameNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
