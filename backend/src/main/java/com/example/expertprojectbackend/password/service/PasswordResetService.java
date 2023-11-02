package com.example.expertprojectbackend.password.service;

import com.example.expertprojectbackend.password.dto.ResetPasswordDto;
import com.example.expertprojectbackend.password.dto.ResetPasswordRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface PasswordResetService {
    void requestPasswordReset(ResetPasswordRequest resetRequestDto, HttpServletRequest request);

    void validateResetToken(String token);

    void resetPassword(String token, ResetPasswordDto resetPasswordDto);
}
