package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size; // برای اعتبارسنجی

@Schema(description = "Data Transfer Object for new user registration")
public class UserRegisterDTO {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Unique username for the new user", example = "newuser123", required = true)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "Password for the new user (will be hashed)", example = "SecurePass!1", required = true)
    private String password;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    @Schema(description = "Display name or full name of the new user", example = "Ali Ahmadi", required = true)
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Optional description or notes about the user", example = "New employee in sales department.", nullable = true)
    private String description;

    // Constructors
    public UserRegisterDTO() {}

    public UserRegisterDTO(String username, String password, String title, String description) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.description = description;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}