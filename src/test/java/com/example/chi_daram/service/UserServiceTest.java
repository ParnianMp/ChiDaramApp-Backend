package com.example.chi_daram.service;

import com.example.chi_daram.dto.*;
//import com.example.chi_daram.entity.User;
import com.example.chi_daram.repository.UserRepository;
//import com.example.chi_daram.service.SmsIrSmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SmsIrSmsService smsService; // Mock کردن سرویس SMS

    private String jwtToken;
    private Long createdUserId;

    @BeforeEach
    void setUp() throws Exception {
        userRepository.deleteAll();

        // جلوگیری از تماس واقعی به SMS.ir
        Mockito.doNothing().when(smsService).sendSms(anyString(), anyString());

        // ثبت‌نام کاربر تستی
        UserRegisterDTO registerDTO = new UserRegisterDTO(
                "testuser_int", "password_int", "Integration Test User", "Desc", "09121234567");

        MvcResult registerResult = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String registerResponseBody = registerResult.getResponse().getContentAsString();
        UserResponseDTO registeredUser =
                objectMapper.readValue(registerResponseBody, UserResponseDTO.class);
        createdUserId = registeredUser.getId();
        assertNotNull(createdUserId, "شناسه کاربر نباید null باشد");

        // تلاش برای لاگین
        LoginRequest loginRequest = new LoginRequest("testuser_int", "password_int");
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn();

        if (loginResult.getResponse().getStatus() == 403) {
            String responseBody = loginResult.getResponse().getContentAsString();
            assertTrue(responseBody.contains("OTP verification required"));

            // درخواست OTP
            mockMvc.perform(post("/api/auth/otp/request")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new OtpRequest(registerDTO.getUsername()))))
                    .andExpect(status().isOk());

            String otpCode = userRepository.findByUsername(registerDTO.getUsername())
                    .orElseThrow(() -> new RuntimeException("کاربر یافت نشد"))
                    .getOtpCode();
            assertNotNull(otpCode, "OTP نباید null باشد");

            // تأیید OTP
            MvcResult otpVerifyResult = mockMvc.perform(post("/api/auth/otp/verify")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(
                            new OtpVerificationRequest(registerDTO.getUsername(), otpCode))))
                    .andExpect(status().isOk())
                    .andReturn();

            JwtResponse jwtResponse =
                    objectMapper.readValue(otpVerifyResult.getResponse().getContentAsString(), JwtResponse.class);
            jwtToken = jwtResponse.getAccessToken();

        } else if (loginResult.getResponse().getStatus() == 200) {
            JwtResponse jwtResponse =
                    objectMapper.readValue(loginResult.getResponse().getContentAsString(), JwtResponse.class);
            jwtToken = jwtResponse.getAccessToken();
        } else {
            fail("وضعیت لاگین غیرمنتظره: " + loginResult.getResponse().getStatus());
        }

        assertNotNull(jwtToken, "JWT نباید null باشد");
    }

    @Test
    @Order(1)
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testuser_int")));
    }

    @Test
    @Order(2)
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/" + createdUserId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("testuser_int")));
    }

    @Test
    @Order(3)
    void testUpdateUser() throws Exception {
        UserUpdateDTO updateDTO = new UserUpdateDTO("newuser", "newpassword", "New Title", "New Desc", "09124445566");

        mockMvc.perform(put("/api/users/" + createdUserId)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                // Assertions updated to validate the JSON response
                .andExpect(jsonPath("$.id").value(createdUserId))
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.description").value("New Desc"));
    }

    @Test
    @Order(4)
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/" + createdUserId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
        assertFalse(userRepository.findById(createdUserId).isPresent());
    }
}