package com.example.chi_daram.service.impl;

import com.example.chi_daram.dto.UserResponseDTO;
import com.example.chi_daram.dto.UserRegisterDTO;
import com.example.chi_daram.entity.User;
import com.example.chi_daram.repository.UserRepository;
import com.example.chi_daram.service.UserService;
import com.example.chi_daram.exception.ResourceNotFoundException;
import com.example.chi_daram.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet; // اضافه شد
import java.util.List;
import java.util.Optional;
import java.util.Set; // اضافه شد
import java.util.stream.Collectors;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO registerNewUser(UserRegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username '" + registerDTO.getUsername() + "' already exists.");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setTitle(registerDTO.getTitle());
        user.setDescription(registerDTO.getDescription());
        user.setRoles(Collections.singleton("USER")); // نقش پیش فرض

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRegisterDTO updateDTO) {
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!existingUser.getUsername().equalsIgnoreCase(updateDTO.getUsername()) &&
            userRepository.findByUsername(updateDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Another user with username '" + updateDTO.getUsername() + "' already exists.");
        }

        existingUser.setUsername(updateDTO.getUsername());
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }
        existingUser.setTitle(updateDTO.getTitle());
        existingUser.setDescription(updateDTO.getDescription());

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID for deletion cannot be null.");
        }
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(userToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID for retrieval cannot be null.");
        }
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        return userRepository.findByUsername(username);
    }

    private UserResponseDTO convertToDto(User user) {
        Set<String> rolesSet = user.getRoles() != null ? user.getRoles() : new HashSet<>(); // تغییر به Set<String>
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getTitle(),
                user.getDescription(),
                rolesSet, // حالا Set<String> را پاس می‌دهیم
                user.isEnabled()
        );
    }
}