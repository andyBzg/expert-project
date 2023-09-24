package com.example.expertprojectbackend.password.controller;

import com.example.expertprojectbackend.password.dto.PasswordResetDto;
import com.example.expertprojectbackend.password.dto.PasswordResetRequestDto;
import com.example.expertprojectbackend.password.service.PasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PasswordResetControllerTest {

    @Mock
    private PasswordResetService passwordResetService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private PasswordResetController passwordResetController;


    @Test
    void requestPasswordReset_success() {
        // Given
        String email = "test@mail.com";
        PasswordResetRequestDto resetRequestDto = new PasswordResetRequestDto(email);

        // When
        ResponseEntity<String> actual = passwordResetController.requestPasswordReset(resetRequestDto, httpServletRequest);

        // Then
        verify(passwordResetService, times(1)).requestPasswordReset(resetRequestDto, httpServletRequest);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNull(actual.getBody());
    }

    @Test
    void validateResetToken_success() {
        // Given
        String token = "testToken";

        // When
        ResponseEntity<String> actual = passwordResetController.validateResetToken(token);

        // Then
        verify(passwordResetService, times(1)).validateResetToken(token);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals("Token is valid.", actual.getBody());
    }

    @Test
    void resetPassword_success() {
        // Given
        String token = "testToken";
        String password = "password";
        String passwordConfirmation = "password";
        PasswordResetDto passwordResetDto = new PasswordResetDto(password, passwordConfirmation);

        // When
        ResponseEntity<String> actual = passwordResetController.resetPassword(token, passwordResetDto);

        // Then
        verify(passwordResetService, times(1)).resetPassword(token, passwordResetDto);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNull(actual.getBody());
    }
}
