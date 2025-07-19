package com.example.chi_daram.service;


public interface OtpService {
    void generateAndSendOtp(String username);
    boolean verifyOtp(String username, String otpCode);
    boolean isOtpEnabledForLogin(String username);
    void setOtpEnabledForLogin(String username, boolean enable);
}
