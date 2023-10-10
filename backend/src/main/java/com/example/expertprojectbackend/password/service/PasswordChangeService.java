package com.example.expertprojectbackend.password.service;

import com.example.expertprojectbackend.password.dto.PasswordChangeDto;

public interface PasswordChangeService {
    void changePassword(String currentUsername, PasswordChangeDto passwordChangeDto);
}
