package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request Data Transfer Object for user login")
public class LoginRequest {

    @NotBlank(message = "Username cannot be empty")
    @Schema(description = "Username for authentication", example = "johndoe", required = true)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "Password for authentication", example = "password123", required = true)
    private String password;

    // Constructors
    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}