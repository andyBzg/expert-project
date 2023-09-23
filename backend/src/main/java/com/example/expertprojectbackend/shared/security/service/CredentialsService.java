package com.example.expertprojectbackend.shared.security.service;

import com.example.expertprojectbackend.shared.dto.ChangePasswordDto;

import java.util.List;

public interface CredentialsService {
    void changePassword(String currentUsername, ChangePasswordDto changePasswordDto);

    void registerCredentials(String email, String encodedPassword);

    void deleteUser(String username);

    void deleteUnverifiedUsers(List<String> usernames);
}
