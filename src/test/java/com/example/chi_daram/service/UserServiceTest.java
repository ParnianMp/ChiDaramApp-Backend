package com.example.chi_daram.service;

import com.example.chi_daram.dto.UserRegisterDTO;
import com.example.chi_daram.dto.UserResponseDTO;
import com.example.chi_daram.entity.User; // **مسیر ایمپورت User به entity تغییر کرد**
import com.example.chi_daram.repository.UserRepository;
import com.example.chi_daram.exception.ResourceNotFoundException;
import com.example.chi_daram.service.impl.UserServiceImpl; // اگر UserService یک Interface است و UerServiceImpl پیاده ساز آن
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
import static org.mockito.ArgumentMatchers.any; // **ایمپورت any به صورت صریح**


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService; // **به UserServiceImpl تغییر دادم تا متدها را پیدا کند**

    private User user;
    private UserRegisterDTO userRegisterDTO;

    @BeforeEach
    void setUp() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        // سازنده User را بر اساس User.java شما تنظیم کردم:
        // User(Long id, String username, String password, String title, String description, Set<String> roles, boolean enabled)
        user = new User(1L, "testuser", "encodedPassword", "Test Title", "Test Desc", roles, true);
        userRegisterDTO = new UserRegisterDTO("testuser", "password123", "Test Title", "Test Desc");
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
    void testRegisterNewUser() { // نام متد از createUser به registerNewUser تغییر یافت
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty()); // استفاده از findByUsername
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO createdUser = userService.registerNewUser(userRegisterDTO); // فراخوانی registerNewUser

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser"); // verify findByUsername
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }
}