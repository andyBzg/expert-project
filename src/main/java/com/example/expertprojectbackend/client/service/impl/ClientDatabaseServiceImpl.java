package com.example.expertprojectbackend.client.service.impl;

import com.example.expertprojectbackend.client.dto.ClientRegistrationDto;
import com.example.expertprojectbackend.client.entity.Client;
import com.example.expertprojectbackend.client.repository.ClientRepository;
import com.example.expertprojectbackend.client.service.ClientDatabaseService;
import com.example.expertprojectbackend.security.roles.Role;
import com.example.expertprojectbackend.shared.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientDatabaseServiceImpl implements ClientDatabaseService {

    private final ClientRepository clientRepository;
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerNewClient(ClientRegistrationDto clientRegistrationDto) {
        String email = clientRegistrationDto.getEmail();
        String encodedPassword = passwordEncoder.encode(clientRegistrationDto.getPassword());

        if (userDetailsManager.userExists(email)) {
            throw new UserAlreadyExistsException("Unable to register username, already exists in DB");
        }

        registerCredentials(email, encodedPassword);
        saveClientToDatabase(clientRegistrationDto);
        log.info("new client successfully registered");
    }

    private void saveClientToDatabase(ClientRegistrationDto clientRegistrationDto) {
        Client client = new Client();
        client.setFirstName(clientRegistrationDto.getFirstName());
        client.setLastName(clientRegistrationDto.getLastName());
        client.setEmail(clientRegistrationDto.getEmail());
        clientRepository.save(client);
        log.info("new client saved to DB");
    }

    private void registerCredentials(String email, String encodedPassword) {
        userDetailsManager.createUser(User.withUsername(email)
                .password(encodedPassword)
                .roles(Role.CLIENT.name())
                .build());
        log.info("credentials registered");
    }
}
