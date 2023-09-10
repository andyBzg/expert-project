package com.example.expertprojectbackend.client.service.database.impl;

import com.example.expertprojectbackend.client.dto.ClientRegistrationDto;
import com.example.expertprojectbackend.client.entity.Client;
import com.example.expertprojectbackend.client.entity.ClientVerificationToken;
import com.example.expertprojectbackend.client.event.ClientRegistrationEvent;
import com.example.expertprojectbackend.client.repository.ClientRepository;
import com.example.expertprojectbackend.client.repository.ClientVerificationTokenRepository;
import com.example.expertprojectbackend.client.service.database.ClientService;
import com.example.expertprojectbackend.security.roles.Role;
import com.example.expertprojectbackend.shared.exception.UserAlreadyExistsException;
import com.example.expertprojectbackend.shared.service.CredentialsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final JdbcUserDetailsManager userDetailsManager;
    private final CredentialsService credentialsService;
    private final ApplicationEventPublisher eventPublisher;
    private final ClientVerificationTokenRepository tokenRepository;

    @Override
    @Transactional
    public void registerNewClient(ClientRegistrationDto clientRegistrationDto, HttpServletRequest request) {
        String email = clientRegistrationDto.getEmail();
        String password = clientRegistrationDto.getPassword();
        String applicationUrl = getApplicationUrl(request);

        if (userDetailsManager.userExists(email)) {
            throw new UserAlreadyExistsException("Unable to register username, already exists in DB");
        }

        credentialsService.registerCredentials(email, password, Role.CLIENT.name());
        saveClientToDatabase(clientRegistrationDto, applicationUrl);
        log.info("New client successfully registered and waiting for email verification");
    }

    @Override
    public void saveClientVerificationToken(String token, Client client) {
        ClientVerificationToken clientVerificationToken = new ClientVerificationToken(token, client);
        tokenRepository.save(clientVerificationToken);
    }

    @Override
    public void saveClientToDatabase(ClientRegistrationDto registrationDto, String applicationUrl) {
        Client client = new Client();
        client.setFirstName(registrationDto.getFirstName());
        client.setLastName(registrationDto.getLastName());
        client.setEmail(registrationDto.getEmail());
        clientRepository.save(client);
        log.info("New client saved to DB");
        eventPublisher.publishEvent(new ClientRegistrationEvent(client, applicationUrl));
    }

    public String getApplicationUrl(HttpServletRequest request) {
        return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
