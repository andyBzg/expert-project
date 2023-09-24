package com.example.expertprojectbackend.security.service;

import com.example.expertprojectbackend.security.database.User;
import com.example.expertprojectbackend.security.roles.Role;

import java.util.List;

public interface UserService {

    void registerCredentials(String email, String encodedPassword);

    void enableUserWithRole(User user, Role role);

    User findByUsername(String username);

    void deleteUser(String username);

    void deleteUnverifiedUsers(List<String> usernames);

    boolean userExists(String username);
}
