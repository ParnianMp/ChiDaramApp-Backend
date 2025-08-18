package com.example.chi_daram.service;

import com.example.chi_daram.dto.UserResponseDTO;
import com.example.chi_daram.dto.UserRegisterDTO;
import com.example.chi_daram.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDTO registerNewUser(UserRegisterDTO dto);
    UserResponseDTO updateUser(Long id, UserRegisterDTO dto);
    void deleteUser(Long id);
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
    Optional<User> getUserByUsername(String username);
}