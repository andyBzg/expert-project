package com.example.expertprojectbackend.shared.listener;

import com.example.expertprojectbackend.shared.event.UserRegistrationEvent;
import com.example.expertprojectbackend.email_verification.service.VerificationTokenService;
import com.example.expertprojectbackend.email_verification.token.VerificationToken;
import com.example.expertprojectbackend.security.user.User;
import com.example.expertprojectbackend.shared.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationListener implements ApplicationListener<UserRegistrationEvent> {

    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        User user = event.getUser();
        String email = user.getUsername();

        VerificationToken verificationToken = verificationTokenService.createVerificationToken(user.getUsername());

        String url = event.getApplicationUrl() + "/api/v1/verify/email?token=" + verificationToken.getToken();

        emailService.sendRegistrationConfirmationEmail(email, url);
    }
}
