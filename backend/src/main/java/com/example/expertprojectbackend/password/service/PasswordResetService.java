package com.example.expertprojectbackend.password.service;

import com.example.expertprojectbackend.password.dto.PasswordResetDto;
import com.example.expertprojectbackend.password.dto.PasswordResetRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface PasswordResetService {
    void requestPasswordReset(PasswordResetRequestDto resetRequestDto, HttpServletRequest request);

    void validateResetToken(String token);

    void resetPassword(String token, PasswordResetDto passwordResetDto);
}
