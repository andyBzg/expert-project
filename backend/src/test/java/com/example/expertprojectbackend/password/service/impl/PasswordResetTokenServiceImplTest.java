package com.example.expertprojectbackend.password.service.impl;

import com.example.expertprojectbackend.password.repository.PasswordResetTokenRepository;
import com.example.expertprojectbackend.password.token.PasswordResetToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenServiceImplTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private PasswordResetTokenServiceImpl passwordResetTokenService;

    @Test
    void createPasswordResetToken_success() {
        // Given
        String username = "test@example.com";

        // When
        PasswordResetToken actual = passwordResetTokenService.createPasswordResetToken(username);

        // Then
        assertNotNull(actual);
        assertEquals(username, actual.getUsername());
        assertNotNull(actual.getToken());
        assertNotNull(actual.getExpirationTime());
        verify(passwordResetTokenRepository, times(1)).save(any(PasswordResetToken.class));
    }

    @Test
    void findByToken_success() {
        // Given
        String token = "exampleToken";
        PasswordResetToken expected = new PasswordResetToken();
        expected.setToken(token);
        when(passwordResetTokenRepository.findByToken(token)).thenReturn(expected);

        // When
        PasswordResetToken actual = passwordResetTokenService.findByToken(token);

        // Then
        assertEquals(expected, actual);
        assertEquals(expected.getToken(), actual.getToken());
    }

    @Test
    void validateToken_validToken_success() {
        // Given
        PasswordResetToken validToken = new PasswordResetToken();
        validToken.setExpirationTime(Instant.now().plusSeconds(60)); // Valid for 1 minute

        // When
        boolean isValid = passwordResetTokenService.validateToken(validToken);

        // Then
        assertTrue(isValid);
    }

    @Test
    void validateToken_expiredToken_success() {
        // Given
        PasswordResetToken validToken = new PasswordResetToken();
        validToken.setExpirationTime(Instant.now().minusSeconds(60)); // Expired for 1 minute

        // When
        boolean isValid = passwordResetTokenService.validateToken(validToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void isTokenExpired_notExpired_success() {
        // Given
        PasswordResetToken validToken = new PasswordResetToken();
        validToken.setExpirationTime(Instant.now().plusSeconds(60)); // Valid for 1 minute

        // When
        boolean isExpired = passwordResetTokenService.isTokenExpired(validToken);

        // Then
        assertFalse(isExpired);
    }

    @Test
    void isTokenExpired_expired_success() {
        // Given
        PasswordResetToken expiredToken = new PasswordResetToken();
        expiredToken.setExpirationTime(Instant.now().minusSeconds(60)); // Expired for 1 minute

        // When
        boolean isExpired = passwordResetTokenService.isTokenExpired(expiredToken);

        // Then
        assertTrue(isExpired);
    }

    @Test
    void saveTokenToDatabase() {
        // Given
        PasswordResetToken resetToken = new PasswordResetToken();

        // When
        passwordResetTokenService.saveTokenToDatabase(resetToken);

        // Then
        verify(passwordResetTokenRepository, times(1)).save(resetToken);
    }

    @Test
    void generateToken_success() {
        // When
        String actual = passwordResetTokenService.generateToken();

        // Then
        assertNotNull(actual);
        assertTrue(actual.matches("[a-f0-9-]+"));
    }

    @Test
    void calculateTokenExpirationTime() {
        // When
        Instant expirationTime = passwordResetTokenService.calculateTokenExpirationTime();

        // Then
        assertNotNull(expirationTime);
    }
}
