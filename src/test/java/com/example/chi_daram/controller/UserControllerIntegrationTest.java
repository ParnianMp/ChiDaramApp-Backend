package com.example.chi_daram.controller;

import com.example.chi_daram.dto.UserRegisterDTO;
import com.example.chi_daram.dto.UserResponseDTO;
import com.example.chi_daram.entity.User; // **مسیر ایمپورت User به entity تغییر کرد**
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

        UserRegisterDTO registerDTO = new UserRegisterDTO("testuser_int", "password_int", "Integration Test User", "Desc");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated());

        UserRegisterDTO loginDTO = new UserRegisterDTO("testuser_int", "password_int", null, null);
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        UserResponseDTO loginResponse = objectMapper.readValue(responseContent, UserResponseDTO.class);
        jwtToken = loginResponse.getAccessToken();
    }

    @Test
    void testGetAllUsers_Authenticated() throws Exception {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        userRepository.save(new User(null, "anotheruser", passwordEncoder.encode("pass"), "Another User", "Another Desc", roles, true));

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
        UserRegisterDTO updateDTO = new UserRegisterDTO("updated_user_int", "new_password_int", "Updated Title", "Updated Desc");

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