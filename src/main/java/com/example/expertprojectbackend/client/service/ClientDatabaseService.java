package com.example.expertprojectbackend.client.service;

import com.example.expertprojectbackend.client.dto.ClientRegistrationDto;

public interface ClientDatabaseService {
    void registerNewClient(ClientRegistrationDto registrationDto);

}
