package com.example.expertprojectbackend.client.service.event_listener;

import com.example.expertprojectbackend.client.entity.Client;
import com.example.expertprojectbackend.client.event.ClientRegistrationEvent;
import com.example.expertprojectbackend.client.service.database.ClientService;
import com.example.expertprojectbackend.shared.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientRegistrationListener implements ApplicationListener<ClientRegistrationEvent> {

    private final EmailService emailService;
    private final ClientService clientService;

    @Override
    public void onApplicationEvent(ClientRegistrationEvent event) {
        Client client = event.getClient();
        String email = client.getEmail();

        String verificationToken = generateVerificationToken();

        clientService.saveClientVerificationToken(verificationToken, client);

        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;

        emailService.sendRegistrationConfirmationEmail(email, url);
    }

    private String generateVerificationToken() {
        log.info("Token generated");
        return UUID.randomUUID().toString();
    }
}
