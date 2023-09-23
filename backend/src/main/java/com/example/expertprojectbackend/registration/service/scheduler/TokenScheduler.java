package com.example.expertprojectbackend.registration.service.scheduler;

import com.example.expertprojectbackend.registration.repository.VerificationTokenRepository;
import com.example.expertprojectbackend.shared.security.service.CredentialsService;
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
public class TokenScheduler {

    private final VerificationTokenRepository tokenRepository;
    private final CredentialsService credentialsService;

    @Scheduled(cron = "${token.removal.schedule}")
    @Transactional
    public void deleteAllExpiredTokens() {
        Instant currentTime = Instant.now();
        List<Long> expiredTokenIds = tokenRepository.findExpiredTokenIds(currentTime);
        List<String> expiredTokenUsernames;

        if (!expiredTokenIds.isEmpty()) {
            expiredTokenUsernames = tokenRepository.findExpiredTokenUsernames(expiredTokenIds);
            tokenRepository.deleteTokensByIds(expiredTokenIds);
            credentialsService.deleteUnverifiedUsers(expiredTokenUsernames);
            log.info("Expired tokens and unverified users deleted");
        }
    }
}
