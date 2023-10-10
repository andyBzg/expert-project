package com.example.expertprojectbackend.registration.scheduler;

import com.example.expertprojectbackend.registration.service.VerificationTokenService;
import com.example.expertprojectbackend.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenCleanerScheduler {

    private final VerificationTokenService verificationTokenService;
    private final UserService userService;

    @Scheduled(cron = "${token.removal.schedule}")
    @Transactional
    public void deleteAllExpiredTokens() {
        Instant currentTime = Instant.now();
        List<Long> expiredTokenIds = verificationTokenService.findExpiredTokenIds(currentTime);
        List<String> expiredTokenUsernames;

        if (!expiredTokenIds.isEmpty()) {
            expiredTokenUsernames = verificationTokenService.findExpiredTokenUsernames(expiredTokenIds);
            verificationTokenService.deleteTokensByIds(expiredTokenIds);
            userService.deleteUnverifiedUsers(expiredTokenUsernames);
            log.info("Expired tokens and unverified users deleted");
        }
    }
}
