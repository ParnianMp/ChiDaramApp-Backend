package com.example.chi_daram.service.impl;

import com.example.chi_daram.entity.User;
import com.example.chi_daram.repository.UserRepository;
import com.example.chi_daram.exception.ResourceNotFoundException;
import com.example.chi_daram.service.OtpService;
import com.example.chi_daram.service.SmsIrSmsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Random;

@Service("otpServiceImpl")
public class OtpServiceImpl implements OtpService {

    private final UserRepository userRepository;
    private final SmsIrSmsService smsService;

    private static final long OTP_VALID_DURATION_MINUTES = 5;

    public OtpServiceImpl(UserRepository userRepository, SmsIrSmsService smsService) {
        this.userRepository = userRepository;
        this.smsService = smsService;
    }

    @Override
    @Transactional
    public void generateAndSendOtp(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("User does not have a phone number to send OTP.");
        }

        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);

        user.setOtpCode(String.valueOf(otp));
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);

        // Changed to send only otpCode, as SmsIrSmsService now uses template
        smsService.sendSms(user.getPhoneNumber(), String.valueOf(otp));
    }

    @Override
    @Transactional
    public boolean verifyOtp(String username, String otpCode) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        if (user.getOtpCode() == null || user.getOtpGeneratedTime() == null) {
            throw new IllegalArgumentException("No OTP has been generated for this user or it has been used/expired.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime otpGeneratedTime = user.getOtpGeneratedTime();
        if (Duration.between(otpGeneratedTime, now).toMinutes() > OTP_VALID_DURATION_MINUTES) {
            user.setOtpCode(null);
            user.setOtpGeneratedTime(null);
            userRepository.save(user);
            throw new IllegalArgumentException("OTP has expired. Please request a new one.");
        }

        boolean isValid = user.getOtpCode().equals(otpCode);

        if (isValid) {
            user.setOtpCode(null);
            user.setOtpGeneratedTime(null);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Invalid OTP code.");
        }
        return isValid;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOtpEnabledForLogin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        return user.isOtpEnabledForLogin();
    }

    @Override
    @Transactional
    public void setOtpEnabledForLogin(String username, boolean enable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        user.setOtpEnabledForLogin(enable);
        if (!enable) {
            user.setOtpCode(null);
            user.setOtpGeneratedTime(null);
        }
        userRepository.save(user);
    }
}
