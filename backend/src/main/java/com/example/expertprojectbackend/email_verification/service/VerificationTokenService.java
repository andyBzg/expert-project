package com.example.expertprojectbackend.email_verification.service;

import com.example.expertprojectbackend.email_verification.token.VerificationToken;

import java.time.Instant;
import java.util.List;

public interface VerificationTokenService {
    VerificationToken createVerificationToken(String username);

    VerificationToken findByToken(String token);

    VerificationToken findByUsername(String username);

    List<Long> findExpiredTokenIds(Instant currentTime);

    List<String> findExpiredTokenUsernames(List<Long> expiredTokenIds);

    boolean validateToken(VerificationToken verificationToken);

    Instant calculateTokenExpirationTime();

    void saveTokenToDatabase(VerificationToken verificationToken);

    void deleteTokenFromDatabase(VerificationToken verificationToken);

    void deleteTokensByIds(List<Long> expiredTokenIds);

    void deleteRevokedTokens();
}
