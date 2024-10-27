package com.WhyU.services;

import com.WhyU.dto.UserDTO;
import com.WhyU.models.User;

import java.util.List;

public interface UserService {
    public User findUserById(Long id);
    public User findUserByUsername(String username);
    public List<User> findAllUsers();
    public User createUser(UserDTO dto);
    public User updateUser(Long id, UserDTO dto);
    public void deleteUserById(Long id);
}
