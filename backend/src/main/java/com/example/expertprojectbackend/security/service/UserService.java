package com.example.expertprojectbackend.security.service;

import com.example.expertprojectbackend.security.user.User;

import java.util.List;

public interface UserService {

    User registerCredentials(String username, String password);

    void enableUser(User user);

    User findByUsername(String username);

    void changePassword(String username, String newPassword);

    void deleteUser(String username);

    void deleteUnverifiedUsers(List<String> usernames);

    boolean userExists(String username);
}
