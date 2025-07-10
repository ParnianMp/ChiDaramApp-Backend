package com.example.chi_daram.controller;

import com.example.chi_daram.dto.JwtResponse;
import com.example.chi_daram.dto.LoginRequest;
import com.example.chi_daram.dto.UserRegisterDTO;
import com.example.chi_daram.dto.UserResponseDTO;
import com.example.chi_daram.entity.User;
import com.example.chi_daram.security.jwt.JwtUtils;
import com.example.chi_daram.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// OpenAPI Imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody; // برای RequestBody در Swagger

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for user registration and login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token upon successful login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))), // JwtResponse شامل accessToken است
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid username or password",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))) // استفاده از ErrorDetails برای خطاها
    })
    public ResponseEntity<?> authenticateUser(
            @RequestBody(description = "User login credentials", required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody LoginRequest loginRequest) {
        // بلوک try-catch برای AuthenticationException باید باقی بماند
        // زیرا GlobalExceptionHandler به طور پیش فرض AuthenticationException را مدیریت نمی‌کند.
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            org.springframework.security.core.userdetails.User userDetails =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            User userEntity = userService.getUserByUsername(userDetails.getUsername())
                                        .orElseThrow(() -> new IllegalStateException("User not found in DB after authentication!"));

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt, userEntity.getId(), userDetails.getUsername(), userEntity.getTitle(), roles));

        } catch (Exception e) {
            // این خطا را به عنوان 401 برمی‌گرداند.
            // می‌توانید از ErrorDetails برای پاسخ استانداردتر استفاده کنید اگر AuthEntryPointJwt آن را مدیریت نمی‌کند.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Invalid username or password!");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user account in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "409", description = "Username already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody(description = "User registration details", required = true,
                    content = @Content(schema = @Schema(implementation = UserRegisterDTO.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody UserRegisterDTO registerDTO) {
        // try-catch حذف شد، GlobalExceptionHandler مدیریت می‌کند
        UserResponseDTO newUser = userService.registerNewUser(registerDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}