package com.example.chi_daram.dto;


import io.swagger.v3.oas.annotations.media.Schema; 

@Schema(description = "Data Transfer Object for user updates")
public class UserUpdateDTO {
    @Schema(description = "New username for the user", example = "updateduser", nullable = true)
    private String username;

    @Schema(description = "New password for the user", example = "newsecurepassword", nullable = true)
    private String password;

    @Schema(description = "New display name or full name of the user", example = "Updated User", nullable = true)
    private String title;

    @Schema(description = "New description for the user", example = "Updated user details.", nullable = true)
    private String description;

    @Schema(description = "New phone number for OTP verification and contact", example = "09129876543", nullable = true)
    private String phoneNumber;

    // Constructors
    public UserUpdateDTO() {
        // Default constructor
    }

    public UserUpdateDTO(String username, String password, String title, String description, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
