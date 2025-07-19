package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic response DTO for OTP operations")
public class OtpResponse {

    @Schema(description = "Message indicating the result of the OTP operation", example = "OTP sent to phone number.", required = true)
    private String message;

    @Schema(description = "Status of the OTP operation (e.g., true for success, false for failure)", example = "true", required = true)
    private boolean success;

    // Constructors
    public OtpResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
