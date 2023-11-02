package com.example.expertprojectbackend.password.service;

import com.example.expertprojectbackend.password.token.PasswordResetToken;

public interface PasswordResetTokenService {
    PasswordResetToken createPasswordResetToken(String username);

    PasswordResetToken findByToken(String token);

    boolean validateToken(PasswordResetToken resetToken);

    void saveToken(PasswordResetToken token);

    void deleteRevokedTokens();
}
