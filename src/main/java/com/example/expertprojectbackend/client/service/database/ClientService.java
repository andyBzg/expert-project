package com.example.expertprojectbackend.client.service.database;

import com.example.expertprojectbackend.client.dto.ClientRegistrationDto;
import com.example.expertprojectbackend.client.entity.Client;
import jakarta.servlet.http.HttpServletRequest;

public interface ClientService {

    void registerNewClient(ClientRegistrationDto registrationDto, HttpServletRequest request);

    void saveClientVerificationToken(String token, Client client);

    void saveClientToDatabase(ClientRegistrationDto clientRegistrationDto, String applicationUrl);

}
