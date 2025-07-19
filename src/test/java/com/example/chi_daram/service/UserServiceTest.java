package com.example.chi_daram.service;

import com.example.chi_daram.dto.UserRegisterDTO;
import com.example.chi_daram.dto.UserResponseDTO;
import com.example.chi_daram.entity.User;
import com.example.chi_daram.repository.UserRepository;
import com.example.chi_daram.exception.ResourceNotFoundException;
import com.example.chi_daram.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    // Removed the KavenegarSmsService mock and injection as it's not part of UserServiceImpl
    // @Mock
    // private KavenegarSmsService smsService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRegisterDTO userRegisterDTO;

    @BeforeEach
    void setUp() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        // Updated User constructor to include phoneNumber and other OTP related fields (null/false for simplicity in test)
        // User(Long id, String username, String password, String title, String description, Set<String> roles, boolean enabled, String otpCode, LocalDateTime otpGeneratedTime, boolean otpEnabledForLogin, String phoneNumber)
        user = new User(1L, "testuser", "encodedPassword", "Test Title", "Test Desc", roles, true, null, null, false, "09123456789");
        
        // Updated UserRegisterDTO constructor to include phoneNumber
        userRegisterDTO = new UserRegisterDTO("testuser", "password123", "Test Title", "Test Desc", "09123456789");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserResponseDTO> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(2L));
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void testRegisterNewUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        // Also mock findByPhoneNumber as registerNewUser now checks for it
        when(userRepository.findByPhoneNumber("09123456789")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO createdUser = userService.registerNewUser(userRegisterDTO);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, times(1)).findByPhoneNumber("09123456789"); // Verify phone number check
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Add more tests for updateUser, deleteUser, getUserByUsername if needed
    @Test
    void testUpdateUser() {
        User existingUser = new User(1L, "olduser", "oldpassword", "Old Title", "Old Desc", new HashSet<>(Set.of("USER")), true, null, null, false, "09121112233");
        UserRegisterDTO updateDTO = new UserRegisterDTO("newuser", "newpassword", "New Title", "New Desc", "09124445566");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByPhoneNumber("09124445566")).thenReturn(Optional.empty()); // Mock for phone number check
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user); // Mock to return the updated user

        UserResponseDTO updatedUser = userService.updateUser(1L, updateDTO);

        assertNotNull(updatedUser);
        assertEquals("newuser", updatedUser.getUsername());
        assertEquals("New Title", updatedUser.getTitle());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findByUsername("newuser");
        verify(userRepository, times(1)).findByPhoneNumber("09124445566");
        verify(passwordEncoder, times(1)).encode("newpassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        User userToDelete = new User(1L, "userToDelete", "pass", "Title", "Desc", new HashSet<>(Set.of("USER")), true, null, null, false, "09127778899");
        when(userRepository.findById(1L)).thenReturn(Optional.of(userToDelete));
        doNothing().when(userRepository).delete(userToDelete);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(userToDelete);
    }
}
