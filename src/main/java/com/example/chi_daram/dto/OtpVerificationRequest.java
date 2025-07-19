package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request DTO for OTP verification (SMS-based)")
public class OtpVerificationRequest {

    @NotBlank(message = "Username cannot be empty")
    @Schema(description = "Username of the user", example = "johndoe", required = true)
    private String username;

    @NotBlank(message = "OTP code cannot be empty")
    @Size(min = 6, max = 6, message = "OTP code must be 6 digits")
    @Pattern(regexp = "^[0-9]{6}$", message = "OTP code must be 6 digits")
    @Schema(description = "6-digit OTP code received via SMS", example = "123456", required = true)
    private String otpCode;

    // Constructors
    public OtpVerificationRequest() {}

    public OtpVerificationRequest(String username, String otpCode) {
        this.username = username;
        this.otpCode = otpCode;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }
}


