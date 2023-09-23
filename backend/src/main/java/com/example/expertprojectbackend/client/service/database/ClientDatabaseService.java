package com.example.expertprojectbackend.client.service.database;

import com.example.expertprojectbackend.shared.security.database.User;

public interface ClientDatabaseService {

    void saveClientToDatabase(User user);
}
