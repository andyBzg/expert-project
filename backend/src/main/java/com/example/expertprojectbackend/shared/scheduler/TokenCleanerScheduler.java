package com.example.expertprojectbackend.shared.scheduler;

import com.example.expertprojectbackend.password.service.PasswordResetTokenService;
import com.example.expertprojectbackend.email_verification.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenCleanerScheduler {

    private final VerificationTokenService verificationTokenService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Scheduled(cron = "${token.removal.schedule}")
    public void deleteRevokedEmailVerificationTokens() {
        verificationTokenService.deleteRevokedTokens();
        log.info("Revoked verification tokens deleted");
    }

    @Scheduled(cron = "${token.removal.schedule}")
    public void deleteRevokedPasswordResetTokens() {
        passwordResetTokenService.deleteRevokedTokens();
        log.info("Revoked password reset tokens deleted");
    }
}
