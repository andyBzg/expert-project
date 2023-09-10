package com.example.expertprojectbackend.client.service.database.impl;

import com.example.expertprojectbackend.client.entity.Client;
import com.example.expertprojectbackend.client.repository.ClientRepository;
import com.example.expertprojectbackend.client.service.database.ClientDatabaseService;
import com.example.expertprojectbackend.shared.security.database.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ClientDatabaseServiceImpl implements ClientDatabaseService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void saveClientToDatabase(User user) {
        Client client = new Client();
        client.setEmail(user.getUsername());
        clientRepository.save(client);
        log.info("New client saved to DB");
    }
}
