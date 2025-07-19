package com.example.chi_daram.controller;

import com.example.chi_daram.dto.JwtResponse;
import com.example.chi_daram.dto.LoginRequest;
import com.example.chi_daram.dto.OtpRequest; // Added for OTP
import com.example.chi_daram.dto.OtpResponse; // Added for OTP
import com.example.chi_daram.dto.OtpVerificationRequest; // Added for OTP
import com.example.chi_daram.dto.UserRegisterDTO;
import com.example.chi_daram.dto.UserResponseDTO;
import com.example.chi_daram.entity.User;
import com.example.chi_daram.security.jwt.JwtUtils;
import com.example.chi_daram.service.OtpService; // Added for OTP (interface)
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for user registration, login and OTP management")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final OtpService otpService; // Injected OtpService

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService, OtpService otpService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.otpService = otpService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user with username/password. If OTP is enabled for the user, a 403 Forbidden response is returned, indicating OTP verification is required. Otherwise, a JWT token is returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully (no OTP required)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid username or password",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - OTP verification required",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OtpResponse.class)))
    })
    public ResponseEntity<?> authenticateUser(
            @RequestBody(description = "User login credentials", required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            org.springframework.security.core.userdetails.User userDetails =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            User userEntity = userService.getUserByUsername(userDetails.getUsername())
                                        .orElseThrow(() -> new IllegalStateException("User not found in DB after authentication!"));

            // --- OTP logic in login ---
            if (userEntity.isOtpEnabledForLogin()) {
                // If OTP is enabled for the user, do not return JWT directly.
                // Instead, return a specific response indicating OTP is required.
                // The user must then verify the OTP.
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                     .body(new OtpResponse("OTP verification required. Please use /api/auth/otp/verify endpoint.", false));
            }
            // --- End of OTP logic in login ---

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // If OTP is not enabled, generate and return JWT
            String jwt = jwtUtils.generateJwtToken(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt, userEntity.getId(), userDetails.getUsername(), userEntity.getTitle(), roles));

        } catch (Exception e) {
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
            @ApiResponse(responseCode = "409", description = "Username or Phone number already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody(description = "User registration details", required = true,
                    content = @Content(schema = @Schema(implementation = UserRegisterDTO.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody UserRegisterDTO registerDTO) {
        UserResponseDTO newUser = userService.registerNewUser(registerDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // --- New Endpoints for SMS OTP ---

    @PostMapping("/otp/request")
    @Operation(summary = "Request OTP", description = "Generates and sends an OTP code to the user's registered phone number. Requires pre-existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP sent successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OtpResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or user has no phone number",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<OtpResponse> requestOtp(
            @RequestBody(description = "Username to request OTP for", required = true,
                    content = @Content(schema = @Schema(implementation = OtpRequest.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody OtpRequest otpRequest) {
        otpService.generateAndSendOtp(otpRequest.getUsername());
        return ResponseEntity.ok(new OtpResponse("OTP sent to your registered phone number.", true));
    }

    @PostMapping("/otp/verify")
    @Operation(summary = "Verify OTP and get JWT", description = "Verifies the provided OTP code. If valid, returns a JWT token for authentication. This endpoint is used after initial username/password authentication if OTP is required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP verified successfully, JWT returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid OTP code or OTP expired",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<?> verifyOtpAndLogin(
            @RequestBody(description = "OTP verification details", required = true,
                    content = @Content(schema = @Schema(implementation = OtpVerificationRequest.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody OtpVerificationRequest verificationRequest) {
        boolean isValid = otpService.verifyOtp(verificationRequest.getUsername(), verificationRequest.getOtpCode());

        if (isValid) {
            User userEntity = userService.getUserByUsername(verificationRequest.getUsername())
                    .orElseThrow(() -> new IllegalStateException("User not found after OTP verification!"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userEntity.getUsername(), null, userEntity.getRoles().stream().map(role -> (GrantedAuthority) () -> role).collect(Collectors.toList()));

            String jwt = jwtUtils.generateJwtToken(authentication);

            List<String> roles = userEntity.getRoles().stream().collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt, userEntity.getId(), userEntity.getUsername(), userEntity.getTitle(), roles));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OtpResponse("Invalid OTP or OTP expired.", false));
        }
    }

    @PostMapping("/otp/enable-for-login/{username}")
    @Operation(summary = "Enable OTP requirement for login", description = "Sets OTP as a required step for future logins for this user. Requires authentication (JWT token).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP requirement enabled successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OtpResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<OtpResponse> enableOtpForLogin(
            @Parameter(description = "Username to enable OTP for login", required = true)
            @PathVariable String username) {
        otpService.setOtpEnabledForLogin(username, true);
        return ResponseEntity.ok(new OtpResponse("OTP requirement enabled for login.", true));
    }

    @PostMapping("/otp/disable-for-login/{username}")
    @Operation(summary = "Disable OTP requirement for login", description = "Removes OTP as a required step for future logins for this user. Requires authentication (JWT token).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP requirement disabled successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OtpResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.example.chi_daram.exception.ErrorDetails.class)))
    })
    public ResponseEntity<OtpResponse> disableOtpForLogin(
            @Parameter(description = "Username to disable OTP for login", required = true)
            @PathVariable String username) {
        otpService.setOtpEnabledForLogin(username, false);
        return ResponseEntity.ok(new OtpResponse("OTP requirement disabled for login.", true));
    }
}































/*package com.example.chi_daram.controller;

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
}*/