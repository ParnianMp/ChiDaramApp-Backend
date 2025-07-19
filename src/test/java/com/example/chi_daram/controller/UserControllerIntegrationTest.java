package com.example.chi_daram.controller;

import com.example.chi_daram.dto.JwtResponse;
import com.example.chi_daram.dto.LoginRequest;
import com.example.chi_daram.dto.UserRegisterDTO;
//import com.example.chi_daram.dto.UserResponseDTO;
import com.example.chi_daram.entity.User;
import com.example.chi_daram.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        userRepository.deleteAll();

        // Updated UserRegisterDTO constructor to include phoneNumber
        // Assuming a valid Iranian phone number for testing
        UserRegisterDTO registerDTO = new UserRegisterDTO("testuser_int", "password_int", "Integration Test User", "Desc", "09121234567");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated());

        // Use LoginRequest DTO for login
        LoginRequest loginRequest = new LoginRequest("testuser_int", "password_int");
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        // Changed to JwtResponse as AuthController returns JwtResponse for successful login
        JwtResponse loginResponse = objectMapper.readValue(responseContent, JwtResponse.class);
        jwtToken = loginResponse.getAccessToken(); // Get accessToken from JwtResponse
    }

    @Test
    void testGetAllUsers_Authenticated() throws Exception {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        // Updated User constructor to match the new full constructor in User.java
        // User(Long id, String username, String password, String title, String description,
        //      Set<String> roles, boolean enabled, String otpCode, LocalDateTime otpGeneratedTime,
        //      boolean otpEnabledForLogin, String phoneNumber)
        userRepository.save(new User(null, "anotheruser", passwordEncoder.encode("pass"), "Another User", "Another Desc",
                                     roles, true, null, null, false, "09129876543")); // Line 78

        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").exists());
    }

    @Test
    void testGetUserById_Authenticated() throws Exception {
        User existingUser = userRepository.findByUsername("testuser_int").orElseThrow();

        mockMvc.perform(get("/api/users/" + existingUser.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser_int"));
    }

    @Test
    void testUpdateUser_Authenticated() throws Exception {
        User existingUser = userRepository.findByUsername("testuser_int").orElseThrow();
        // Updated UserRegisterDTO constructor to include phoneNumber
        UserRegisterDTO updateDTO = new UserRegisterDTO("updated_user_int", "new_password_int", "Updated Title", "Updated Desc", "09123456789");

        mockMvc.perform(put("/api/users/" + existingUser.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updated_user_int"))
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void testDeleteUser_Authenticated() throws Exception {
        User userToDelete = userRepository.findByUsername("testuser_int").orElseThrow();

        mockMvc.perform(delete("/api/users/" + userToDelete.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.findById(userToDelete.getId()).isPresent());
    }

    @Test
    void testGetAllUsers_Unauthenticated() throws Exception {
        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
