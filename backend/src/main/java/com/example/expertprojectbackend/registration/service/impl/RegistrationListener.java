package com.example.expertprojectbackend.registration.service.impl;

import com.example.expertprojectbackend.registration.event.UserRegistrationEvent;
import com.example.expertprojectbackend.registration.service.RegistrationService;
import com.example.expertprojectbackend.shared.security.database.User;
import com.example.expertprojectbackend.shared.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationListener implements ApplicationListener<UserRegistrationEvent> {

    private final EmailService emailService;
    private final RegistrationService registrationService;

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        User user = event.getUser();
        String email = user.getUsername();

        String verificationToken = generateVerificationToken();

        registrationService.saveVerificationToken(verificationToken, user);

        String url = event.getApplicationUrl() + "/api/register/verifyEmail?token=" + verificationToken;

        emailService.sendRegistrationConfirmationEmail(email, url);
    }

    private String generateVerificationToken() {
        log.info("Token generated");
        return UUID.randomUUID().toString();
    }
}
