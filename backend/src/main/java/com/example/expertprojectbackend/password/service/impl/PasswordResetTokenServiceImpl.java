package com.example.expertprojectbackend.password.service.impl;

import com.example.expertprojectbackend.password.repository.PasswordResetTokenRepository;
import com.example.expertprojectbackend.password.service.PasswordResetTokenService;
import com.example.expertprojectbackend.password.token.PasswordResetToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${password.reset.token.expiration.minutes}")
    private int expirationTimeMinutes;

    @Override
    public PasswordResetToken createPasswordResetToken(String username) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(generateToken());
        passwordResetToken.setExpirationTime(calculateTokenExpirationTime());
        passwordResetToken.setUsername(username);
        saveTokenToDatabase(passwordResetToken);
        log.info("Password reset token created and saved to DB");
        return passwordResetToken;
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public boolean validateToken(PasswordResetToken resetToken) {
        return resetToken != null && !resetToken.isRevoked() && !isTokenExpired(resetToken);
    }

    @Override
    public void saveToken(PasswordResetToken token) {
        passwordResetTokenRepository.save(token);
    }

    public void saveTokenToDatabase(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public void deleteRevokedTokens() {
        passwordResetTokenRepository.deleteAllByRevokedTrue();
    }

    public boolean isTokenExpired(PasswordResetToken resetToken) {
        return resetToken.getExpirationTime().isBefore(Instant.now());
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public Instant calculateTokenExpirationTime() {
        return Instant.now().plus(expirationTimeMinutes, ChronoUnit.MINUTES);
    }
}
