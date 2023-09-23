package com.example.expertprojectbackend.expert.service.event_listener;

import com.example.expertprojectbackend.expert.entity.Expert;
import com.example.expertprojectbackend.expert.event.ExpertRegistrationEvent;
import com.example.expertprojectbackend.expert.service.database.ExpertService;
import com.example.expertprojectbackend.shared.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpertRegistrationListener implements ApplicationListener<ExpertRegistrationEvent> {

    private final EmailService emailService;
    private final ExpertService expertService;

    @Override
    public void onApplicationEvent(ExpertRegistrationEvent event) {
        // 1. get the newly registered user
        Expert expert = event.getExpert();
        String email = expert.getEmail();

        // 2. create a verification token for the user
        String verificationToken = generateVerificationToken();

        // 3. save the verification token for the user
        expertService.saveExpertVerificationToken(verificationToken, expert);

        // 4. build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;

        // 5. send the email
        emailService.sendRegistrationConfirmationEmail(email, url);
    }

    private String generateVerificationToken() {
        log.info("Token generated");
        return UUID.randomUUID().toString();
    }
}
