package com.example.expertprojectbackend.registration.service.impl;

import com.example.expertprojectbackend.registration.repository.VerificationTokenRepository;
import com.example.expertprojectbackend.registration.token.VerificationToken;
import com.example.expertprojectbackend.shared.exception.TokenNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationTokenServiceImplTest {

    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @InjectMocks
    private VerificationTokenServiceImpl verificationTokenService;


    @Test
    void createVerificationToken() {
        // Given
        String username = "user@example.com";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUsername(username);

        // When
        VerificationToken actual = verificationTokenService.createVerificationToken(username);

        // Then
        verify(verificationTokenRepository, times(1)).save(any(VerificationToken.class));
        assertNotNull(actual);
        assertNotNull(actual.getToken());
        assertNotNull(actual.getExpirationTime());
        assertEquals(verificationToken.getUsername(), actual.getUsername());
        assertTrue(actual.getToken().matches("[a-f0-9-]+"));
        assertTrue(actual.getExpirationTime().isBefore(Instant.now()));
    }

    @Test
    void findByToken_validToken_success() {
        // Given
        String token = "exampleToken";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);

        when(verificationTokenRepository.findByToken(token)).thenReturn(Optional.of(verificationToken));

        // When
        VerificationToken actual = verificationTokenService.findByToken(token);
        System.out.println(actual);

        // Then
        assertNotNull(actual);
        assertEquals(token, actual.getToken());
        verify(verificationTokenRepository, times(1)).findByToken(token);
    }

    @Test
    void findByToken_invalidToken_throwsTokenNotFoundException() {
        // Given
        String token = "invalid_token";
        when(verificationTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(TokenNotFoundException.class, () -> verificationTokenService.findByToken(token));

        verify(verificationTokenRepository, times(1)).findByToken(token);
    }

    @Test
    void findByUsername_validUsername_success() {
        // Given
        String username = "test@example.com";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUsername(username);

        when(verificationTokenRepository.findByUsername(username)).thenReturn(Optional.of(verificationToken));

        // When
        VerificationToken actual = verificationTokenService.findByUsername(username);

        // Then
        assertNotNull(actual);
        assertEquals(username, actual.getUsername());
        verify(verificationTokenRepository, times(1)).findByUsername(username);
    }

    @Test
    void findByUsername_invalidUsername_throwsTokenNotFoundException() {
        // Given
        String username = "invalid_username";
        when(verificationTokenRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(TokenNotFoundException.class, () -> verificationTokenService.findByUsername(username));

        verify(verificationTokenRepository, times(1)).findByUsername(username);
    }

    @Test
    void findExpiredTokenIds_returnsListOfTokenIds_success() {
        // Given
        Instant currentTime = Instant.now();
        List<Long> expiredTokenIds = List.of(1L, 2L, 3L);
        when(verificationTokenRepository.findExpiredTokenIds(currentTime)).thenReturn(expiredTokenIds);

        // When
        List<Long> actual = verificationTokenService.findExpiredTokenIds(currentTime);

        // Then
        assertNotNull(actual);
        assertEquals(expiredTokenIds, actual);
    }

    @Test
    void findExpiredTokenIds_noExpiredTokens_returnsEmptyList_success() {
        // Given
        Instant currentTime = Instant.now();
        List<Long> expiredTokenIds = Collections.emptyList();
        when(verificationTokenRepository.findExpiredTokenIds(currentTime)).thenReturn(expiredTokenIds);

        // When
        List<Long> actual = verificationTokenService.findExpiredTokenIds(currentTime);

        // Then
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
        assertEquals(expiredTokenIds, actual);
    }

    @Test
    void findExpiredTokenUsernames_returnsListOfUsernames_success() {
        // Given
        List<Long> expiredTokenIds = List.of(1L, 2L, 3L);
        List<String> expected = List.of("user1", "user2", "user3");
        when(verificationTokenRepository.findExpiredTokenUsernames(expiredTokenIds)).thenReturn(expected);

        // When
        List<String> actual = verificationTokenService.findExpiredTokenUsernames(expiredTokenIds);

        // Then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void findExpiredTokenUsernames_noExpiredTokens_returnsEmptyList_success() {
        // Given
        List<Long> expiredTokenIds = Collections.emptyList();
        List<String> expected = Collections.emptyList();
        when(verificationTokenRepository.findExpiredTokenUsernames(expiredTokenIds)).thenReturn(expected);

        // When
        List<String> actual = verificationTokenService.findExpiredTokenUsernames(expiredTokenIds);

        // Then
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
    }

    @Test
    void validateToken_validToken_returnsTrue_success() {
        // Given
        VerificationToken token = new VerificationToken();
        token.setExpirationTime(Instant.now().plus(1, ChronoUnit.MINUTES));

        // When
        boolean isValid = verificationTokenService.validateToken(token);

        // Then
        assertTrue(isValid);
    }

    @Test
    void validateToken_expiredToken_returnsFalse_success() {
        // Given
        VerificationToken token = new VerificationToken();
        token.setExpirationTime(Instant.now().minus(1, ChronoUnit.MINUTES));

        // When
        boolean isValid = verificationTokenService.validateToken(token);

        // Then
        assertFalse(isValid);
    }

    @Test
    void calculateTokenExpirationTime_success() {
        // When
        Instant expirationTime = verificationTokenService.calculateTokenExpirationTime();

        // Then
        assertNotNull(expirationTime);
    }

    @Test
    void saveTokenToDatabase_success() {
        // Given
        VerificationToken verificationToken = new VerificationToken();

        // When
        verificationTokenService.saveTokenToDatabase(verificationToken);

        // Then
        verify(verificationTokenRepository, times(1)).save(verificationToken);
    }

    @Test
    void deleteTokenFromDatabase_success() {
        // Given
        VerificationToken token = new VerificationToken();

        // When
        verificationTokenService.deleteTokenFromDatabase(token);

        // Then
        verify(verificationTokenRepository, times(1)).delete(token);
    }

    @Test
    void deleteTokensByIds_success() {
        // Given
        List<Long> tokenIds = Arrays.asList(1L, 2L, 3L);

        // When
        verificationTokenService.deleteTokensByIds(tokenIds);

        // Then
        verify(verificationTokenRepository, times(1)).deleteTokensByIds(tokenIds);
    }

    @Test
    void isTokenExpired_validToken_returnsFalse_success() {
        // Given
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setExpirationTime(Instant.now().plus(1, ChronoUnit.MINUTES));

        // When
        boolean isExpired = verificationTokenService.isTokenExpired(verificationToken);

        // Then
        assertFalse(isExpired);
    }

    @Test
    void isTokenExpired_expiredToken_returnsTrue_success() {
        // Given
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setExpirationTime(Instant.now().minus(1, ChronoUnit.MINUTES));

        // When
        boolean isExpired = verificationTokenService.isTokenExpired(verificationToken);

        // Then
        assertTrue(isExpired);
    }

    @Test
    void generateToken_success() {
        // When
        String actual = verificationTokenService.generateToken();

        // Then
        assertNotNull(actual);
        assertTrue(actual.matches("[a-f0-9-]+"));
    }
}
