package com.example.expertprojectbackend.shared.service.impl;

import com.example.expertprojectbackend.shared.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendRegistrationConfirmationEmail(String email, String verificationUrl) {
        // TODO add email sending logic
        log.info("Confirmation email sent. Verification url: {}", verificationUrl);
    }

    @Override
    public void sendPasswordResetEmail(String email, String passwordResetUrl) {
        // TODO add email sending logic
        log.info("Password reset email sent. Reset url: {}", passwordResetUrl);
    }
}
