package com.WhyU.services.impl;

import com.WhyU.dto.UserDTO;
import com.WhyU.models.User;
import com.WhyU.repositories.UserRepository;
import com.WhyU.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id " + id + " не найден!"));
    }

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserDTO dto){
        return userRepository.save(User.builder()
                .username(dto.getUsername())
                .build());
    }

    public User updateUser(Long id, UserDTO dto){
        User selectedUser = userRepository.findById(id).orElse(null);

        if(selectedUser == null){
            return null;
        }
        else {
            selectedUser.setUsername(dto.getUsername() == null ? selectedUser.getUsername() : dto.getUsername());
            selectedUser.setRegDate(dto.getRegDate() == null ? selectedUser.getRegDate() : dto.getRegDate());

            return userRepository.save(selectedUser);
        }
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
