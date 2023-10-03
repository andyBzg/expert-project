package com.example.expertprojectbackend.registration.scheduler;

import com.example.expertprojectbackend.registration.service.VerificationTokenService;
import com.example.expertprojectbackend.security.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenCleanerSchedulerTest {

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TokenCleanerScheduler tokenCleanerScheduler;

    @Test
    void deleteAllExpiredTokens_success() {
        // Given
        List<Long> expiredTokenIds = List.of(1L, 2L, 3L);
        List<String> expiredTokenUsernames = List.of("user1", "user2", "user3");

        when(verificationTokenService.findExpiredTokenIds(any(Instant.class))).thenReturn(expiredTokenIds);
        when(verificationTokenService.findExpiredTokenUsernames(expiredTokenIds)).thenReturn(expiredTokenUsernames);

        // When
        tokenCleanerScheduler.deleteAllExpiredTokens();

        // Then
        verify(verificationTokenService, times(1)).findExpiredTokenIds(any(Instant.class));
        verify(verificationTokenService, times(1)).findExpiredTokenUsernames(expiredTokenIds);
        verify(verificationTokenService, times(1)).deleteTokensByIds(expiredTokenIds);
        verify(userService, times(1)).deleteUnverifiedUsers(expiredTokenUsernames);
    }

    @Test
    void deleteAllExpiredTokens_noExpiredTokens_deletesNothing_success() {
        // Given
        List<Long> expiredTokenIds = Collections.emptyList();

        when(verificationTokenService.findExpiredTokenIds(any(Instant.class))).thenReturn(expiredTokenIds);

        // When
        tokenCleanerScheduler.deleteAllExpiredTokens();

        // Then
        verify(verificationTokenService, times(1)).findExpiredTokenIds(any(Instant.class));
        verifyNoMoreInteractions(verificationTokenService);
        verifyNoInteractions(userService);
    }
}
