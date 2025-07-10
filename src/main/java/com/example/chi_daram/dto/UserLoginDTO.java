package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data Transfer Object for user login request (redundant with LoginRequest, consider merging)")
public class UserLoginDTO {

    @NotBlank(message = "Username cannot be empty")
    @Schema(description = "Username for user authentication", example = "testuser", required = true)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "Password for user authentication", example = "password123", required = true)
    private String password;

    // Constructors
    public UserLoginDTO() {}

    public UserLoginDTO(String username, String password) {
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