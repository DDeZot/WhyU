package com.WhyU.services;

import com.WhyU.dto.UserDTO;
import com.WhyU.models.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);
    User findUserByUsername(String username);
    List<User> findAllUsers();
    User createUser(UserDTO dto);
    User updateUser(Long id, UserDTO dto);
    void deleteUserById(Long id);
}
