package com.WhyU.services.impl;

import com.WhyU.dto.UserDTO;
import com.WhyU.models.Attachment;
import com.WhyU.models.MyUserDetails;
import com.WhyU.models.User;
import com.WhyU.repositories.UserRepository;
import com.WhyU.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final AttachmentServiceImpl attachmentService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AttachmentServiceImpl attachmentService, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.attachmentService = attachmentService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id " + id + " не найден!"));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ником " + username + " не найден!"));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserDTO dto){
        if(userRepository.findByUsername(dto.getUsername()).isPresent())
            return null;

        return userRepository.save(User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .birthDate(dto.getBirthDate())
                .roles("ROLE_USER")
                .build());
    }

    @Transactional
    public User updateUser(Long id, UserDTO dto){
        User selectedUser = userRepository.findById(id).orElse(null);

        if(selectedUser == null)
            return null;

        selectedUser.setUsername(dto.getUsername() == null ? selectedUser.getUsername() :
                dto.getUsername());
        selectedUser.setPassword(dto.getUsername() == null ? selectedUser.getPassword() :
                passwordEncoder.encode(dto.getPassword()));
        selectedUser.setProfilePic(dto.getProfilePic() == null ? selectedUser.getProfilePic() :
                dto.getProfilePic());
        selectedUser.setProfilePic(dto.getProfilePicAttachmentID() == null ? selectedUser.getProfilePic() :
                attachmentService.findAttachmentById(dto.getProfilePicAttachmentID()));
        selectedUser.setRoles(dto.getRoles() == null ? selectedUser.getRoles() :
                dto.getRoles());

        return userRepository.save(selectedUser);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User uploadProfilePic(Long id, MultipartFile image) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null");
        }

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Изображение не может быть пустым");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + id + " не найден"));

        Attachment attachment = attachmentService.createAttachment(image);

        user.setProfilePic(attachment);
        return userRepository.save(user);
    }

    public User changePassword(Long id, String password) {
        User user = findUserById(id);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден"));
    }
}
