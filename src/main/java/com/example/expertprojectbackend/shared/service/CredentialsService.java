package com.example.expertprojectbackend.shared.service;

import com.example.expertprojectbackend.shared.dto.ChangePasswordDto;

public interface CredentialsService {
    void changePassword(String currentUsername, ChangePasswordDto changePasswordDto);
}
