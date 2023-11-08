package com.example.expertprojectbackend.email_verification.service.impl;

import com.example.expertprojectbackend.email_verification.repository.VerificationTokenRepository;
import com.example.expertprojectbackend.email_verification.service.VerificationTokenService;
import com.example.expertprojectbackend.email_verification.token.VerificationToken;
import com.example.expertprojectbackend.shared.exception.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${verification.token.expiration.minutes}")
    private int expirationTimeMinutes;

    @Override
    public VerificationToken createVerificationToken(String username) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(generateToken());
        verificationToken.setExpirationTime(calculateTokenExpirationTime());
        verificationToken.setUsername(username);
        saveTokenToDatabase(verificationToken);
        log.info("Verification token created and saved to DB");
        return verificationToken;
    }

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token not found"));
    }

    @Override
    public VerificationToken findByUsername(String username) {
        return verificationTokenRepository
                .findByUsername(username)
                .orElseThrow(() -> new TokenNotFoundException("Token not found"));
    }

    @Override
    public List<Long> findExpiredTokenIds(Instant currentTime) {
        return verificationTokenRepository.findExpiredTokenIds(currentTime);
    }

    @Override
    public List<String> findExpiredTokenUsernames(List<Long> expiredTokenIds) {
        return verificationTokenRepository.findExpiredTokenUsernames(expiredTokenIds);
    }

    @Override
    public boolean validateToken(VerificationToken verificationToken) {
        return verificationToken != null && !isTokenExpired(verificationToken);
    }

    @Override
    public Instant calculateTokenExpirationTime() {
        return Instant.now().plus(expirationTimeMinutes, ChronoUnit.MINUTES);
    }

    @Override
    public void saveTokenToDatabase(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void deleteTokenFromDatabase(VerificationToken verificationToken) {
        verificationTokenRepository.delete(verificationToken);
    }

    @Override
    public void deleteTokensByIds(List<Long> expiredTokenIds) {
        verificationTokenRepository.deleteTokensByIds(expiredTokenIds);
    }

    @Override
    public void deleteRevokedTokens() {
        verificationTokenRepository.deleteAllByRevokedTrue();
    }

    public boolean isTokenExpired(VerificationToken verificationToken) {
        return verificationToken.getExpirationTime().isBefore(Instant.now());
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
