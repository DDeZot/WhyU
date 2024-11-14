package com.WhyU.services;

import com.WhyU.dto.UserDTO;
import com.WhyU.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User findUserById(Long id);
    User findUserByUsername(String username);
    List<User> findAllUsers();
    User createUser(UserDTO dto);
    User updateUser(Long id, UserDTO dto);
    User uploadProfilePic(Long id, MultipartFile file) throws IOException;
    User changePassword(Long id, String password);
    void deleteUserById(Long id);
}
