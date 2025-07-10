package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Basic Data Transfer Object for User (might be used for roles or simpler user representations)")
public class UserDTO {

    @Schema(description = "Unique identifier of the User", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    @Schema(description = "Display title/name of the user", example = "System Admin", required = true)
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Description or role details for the user", example = "Administrator with full system access.", nullable = true)
    private String description;

    // Constructors
    public UserDTO() {
    }

    public UserDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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