package com.example.chi_daram.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"user_security\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
                     joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    private boolean enabled = true;
    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;

    // --- New fields for SMS OTP ---
    @Column(name = "otp_code")
    private String otpCode; // Generated OTP code

    @Column(name = "otp_generated_time")
    private LocalDateTime otpGeneratedTime; // Time of OTP generation (for expiry check)

    @Column(name = "otp_enabled_for_login", nullable = false)
    private boolean otpEnabledForLogin = false; // Is OTP enabled for login for this user?

    @Column(name = "phone_number", unique = true) // Add phone number field for OTP sending
    private String phoneNumber;
    // --- End of new fields for SMS OTP ---

    public User() {}

    // Constructor with basic user details
    public User(String username, String password, String title, String description) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.description = description;
        this.roles.add("USER");
    }

    // Full constructor including all fields (ID, basic details, roles, enabled, and OTP fields)
    public User(Long id, String username, String password, String title, String description,
                Set<String> roles, boolean enabled, String otpCode, LocalDateTime otpGeneratedTime,
                boolean otpEnabledForLogin, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.title = title;
        this.description = description;
        this.roles = roles;
        this.enabled = enabled;
        this.otpCode = otpCode;
        this.otpGeneratedTime = otpGeneratedTime;
        this.otpEnabledForLogin = otpEnabledForLogin;
        this.phoneNumber = phoneNumber;
    }

    // --- Getters and Setters for new SMS OTP fields ---
    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public LocalDateTime getOtpGeneratedTime() {
        return otpGeneratedTime;
    }

    public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
        this.otpGeneratedTime = otpGeneratedTime;
    }

    public boolean isOtpEnabledForLogin() {
        return otpEnabledForLogin;
    }

    public void setOtpEnabledForLogin(boolean otpEnabledForLogin) {
        this.otpEnabledForLogin = otpEnabledForLogin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    // --- End of Getters and Setters for SMS OTP ---

    // Existing getters and setters are here:
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

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
}















/*package com.example.chi_daram.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"user_security\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 255)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
                     joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    private boolean enabled = true;
    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;

    public User() {}

    public User(String username, String password, String title, String description) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.description = description;
        this.roles.add("USER");
    }

    // **سازنده جدید: این سازنده را اضافه کنید**
    public User(Long id, String username, String password, String title, String description, Set<String> roles, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.title = title;
        this.description = description;
        this.roles = roles;
        this.enabled = enabled;
    }

    // متدهای getter و setter موجود در اینجا قرار دارند و نیازی به تغییر ندارند.
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

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
}*/