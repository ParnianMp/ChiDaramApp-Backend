package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request DTO to initiate OTP sending (e.g., via SMS)")
public class OtpRequest {

    @NotBlank(message = "Username cannot be empty")
    @Schema(description = "Username of the user requesting OTP", example = "johndoe", required = true)
    private String username;

    // Constructors
    public OtpRequest() {}

    public OtpRequest(String username) {
        this.username = username;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}



