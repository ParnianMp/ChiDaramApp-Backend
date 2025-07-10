package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

@Schema(description = "Data Transfer Object for user response (excluding sensitive information like password)")
public class UserResponseDTO {

    @Schema(description = "Unique identifier of the user", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Username of the user", example = "johndoe", required = true)
    private String username;

    @Schema(description = "Display name or full name of the user", example = "John Doe", required = true)
    private String title;

    @Schema(description = "Description or additional details about the user", example = "User account for general access.", nullable = true)
    private String description;

    @Schema(description = "Set of roles assigned to the user", example = "[\"ROLE_USER\", \"ROLE_ACCOUNTANT\"]", required = true)
    private Set<String> roles;

    @Schema(description = "Indicates if the user account is enabled", example = "true", required = true)
    private boolean enabled;

    @Schema(description = "JWT Access Token for authentication (only present during login/registration response)", example = "eyJhbGciOiJIUzUxMiJ9...", nullable = true)
    private String accessToken; // **اضافه شده برای نگهداری توکن JWT**

    // Constructors
    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String username, String title, String description, Set<String> roles, boolean enabled) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.description = description;
        this.roles = roles;
        this.enabled = enabled;
    }

    // **سازنده جدید شامل accessToken - اگر از این سازنده در کد اصلی استفاده می کنید**
    public UserResponseDTO(Long id, String username, String title, String description, Set<String> roles, boolean enabled, String accessToken) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.description = description;
        this.roles = roles;
        this.enabled = enabled;
        this.accessToken = accessToken;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // **متدهای getter و setter برای accessToken - اضافه شده**
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}