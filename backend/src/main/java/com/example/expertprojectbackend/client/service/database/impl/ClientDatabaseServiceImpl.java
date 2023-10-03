package com.example.expertprojectbackend.client.service.database.impl;

import com.example.expertprojectbackend.client.entity.Client;
import com.example.expertprojectbackend.client.repository.ClientRepository;
import com.example.expertprojectbackend.client.service.database.ClientDatabaseService;
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
    public void saveClientToDatabase(Client client) {
        clientRepository.save(client);
        log.info("New client saved to DB");
    }
}
