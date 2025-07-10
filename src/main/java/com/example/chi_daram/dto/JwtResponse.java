package com.example.chi_daram.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Response Data Transfer Object for JWT authentication")
public class JwtResponse {

    @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzUxMiJ9...", required = true)
    private String token;

    @Schema(description = "Type of the token", example = "Bearer")
    private String type = "Bearer"; // نوع توکن

    @Schema(description = "User ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Username of the authenticated user", example = "johndoe", required = true)
    private String username;

    @Schema(description = "Display name of the authenticated user", example = "John Doe", required = true)
    private String title; // نام نمایشی کاربر

    @Schema(description = "List of roles assigned to the user", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]", required = true)
    private List<String> roles; // نقش های کاربر

    // Constructors
    public JwtResponse(String accessToken, Long id, String username, String title, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.title = title;
        this.roles = roles;
    }

    // Getters and Setters
    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}