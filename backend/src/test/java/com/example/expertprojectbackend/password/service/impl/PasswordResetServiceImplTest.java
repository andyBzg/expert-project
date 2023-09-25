package com.example.expertprojectbackend.password.service.impl;

import com.example.expertprojectbackend.password.dto.PasswordResetDto;
import com.example.expertprojectbackend.password.dto.PasswordResetRequestDto;
import com.example.expertprojectbackend.password.service.PasswordResetTokenService;
import com.example.expertprojectbackend.password.token.PasswordResetToken;
import com.example.expertprojectbackend.security.database.User;
import com.example.expertprojectbackend.security.service.UserService;
import com.example.expertprojectbackend.shared.email.EmailService;
import com.example.expertprojectbackend.shared.exception.InvalidTokenException;
import com.example.expertprojectbackend.shared.exception.PasswordMismatchException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordResetTokenService passwordResetTokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private PasswordResetServiceImpl passwordResetService;

    private String email;
    private String token;

    @BeforeEach
    void setUp() {
        email = "test@mail.com";
        token = "exampleToken";
    }

    @Test
    void requestPasswordReset_validData_success() {
        // Given
        PasswordResetRequestDto resetRequestDto = new PasswordResetRequestDto(email);
        PasswordResetToken resetToken = new PasswordResetToken();

        when(userService.userExists(email)).thenReturn(true);
        when(passwordResetTokenService.createPasswordResetToken(email)).thenReturn(resetToken);

        // When
        passwordResetService.requestPasswordReset(resetRequestDto, httpServletRequest);

        // Then
        verify(userService, times(1)).userExists(email);
        verify(passwordResetTokenService, times(1)).createPasswordResetToken(email);
    }

    @Test
    void requestPasswordReset_userNotExist_throwsUsernameNotFoundException() {
        // Given
        PasswordResetRequestDto resetRequestDto = new PasswordResetRequestDto(email);

        when(userService.userExists(email)).thenReturn(false);

        // When
        assertThrows(UsernameNotFoundException.class, () -> passwordResetService.requestPasswordReset(resetRequestDto, httpServletRequest));

        // Then
        verify(userService, times(1)).userExists(email);
        verifyNoInteractions(passwordResetTokenService);
        verifyNoInteractions(emailService);
    }

    @Test
    void validateResetToken_validData_success() {
        // Given
        PasswordResetToken resetToken = new PasswordResetToken();
        when(passwordResetTokenService.findByToken(token)).thenReturn(resetToken);
        when(passwordResetTokenService.validateToken(resetToken)).thenReturn(true);

        User user = new User();
        user.setEnabled(true);
        when(userService.findByUsername(resetToken.getUsername())).thenReturn(user);

        // When
        passwordResetService.validateResetToken(token);

        // Then
        verify(passwordResetTokenService, times(1)).findByToken(token);
        verify(passwordResetTokenService, times(1)).validateToken(resetToken);
        verify(userService, times(1)).findByUsername(resetToken.getUsername());
    }

    @Test
    void validateResetToken_invalidToken_throwsInvalidTokenException() {
        // Given
        token = "invalid_token";
        PasswordResetToken resetToken = new PasswordResetToken();
        when(passwordResetTokenService.findByToken(token)).thenReturn(resetToken);
        when(passwordResetTokenService.validateToken(resetToken)).thenReturn(false);

        // When, Then
        assertThrows(InvalidTokenException.class, () -> passwordResetService.validateResetToken(token));

        verify(passwordResetTokenService, times(1)).findByToken(token);
        verify(passwordResetTokenService, times(1)).validateToken(resetToken);
        verifyNoInteractions(userService);
    }

    @Test
    void validateResetToken_disabledUser_throwsDisabledException() {
        // Given
        PasswordResetToken resetToken = new PasswordResetToken();
        when(passwordResetTokenService.findByToken(token)).thenReturn(resetToken);
        when(passwordResetTokenService.validateToken(resetToken)).thenReturn(true);

        User user = new User();
        user.setEnabled(false);
        when(userService.findByUsername(resetToken.getUsername())).thenReturn(user);

        // When, Then
        assertThrows(DisabledException.class, () -> passwordResetService.validateResetToken(token));

        verify(passwordResetTokenService, times(1)).findByToken(token);
        verify(passwordResetTokenService, times(1)).validateToken(resetToken);
        verify(userService, times(1)).findByUsername(resetToken.getUsername());
    }

    @Test
    void resetPassword_validData_success() {
        // Given
        String password = "password";
        String passwordConfirmation = "password";
        PasswordResetDto passwordResetDto = new PasswordResetDto(password, passwordConfirmation);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUsername(email);

        when(passwordResetTokenService.findByToken(token)).thenReturn(resetToken);
        when(passwordResetTokenService.validateToken(resetToken)).thenReturn(true);
        User user = new User();
        user.setEnabled(true);
        when(userService.findByUsername(email)).thenReturn(user);

        // When
        passwordResetService.resetPassword(token, passwordResetDto);

        // Then
        verify(passwordResetTokenService, times(2)).findByToken(token);
        verify(passwordResetTokenService, times(1)).validateToken(resetToken);
        verify(userService, times(1)).findByUsername(email);
        verify(userService, times(1)).changePassword(email, password);
    }

    @Test
    void resetPassword_incorrectPassword_throwsPasswordMismatchException() {
        // Given
        String password = "incorrect_password";
        String passwordConfirmation = "password";
        PasswordResetDto passwordResetDto = new PasswordResetDto(password, passwordConfirmation);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUsername(email);

        when(passwordResetTokenService.findByToken(token)).thenReturn(resetToken);
        when(passwordResetTokenService.validateToken(resetToken)).thenReturn(true);
        User user = new User();
        user.setEnabled(true);
        when(userService.findByUsername(email)).thenReturn(user);

        // When
        assertThrows(PasswordMismatchException.class, () -> passwordResetService.resetPassword(token, passwordResetDto));

        // Then
        verify(passwordResetTokenService, times(1)).findByToken(token);
        verify(passwordResetTokenService, times(1)).validateToken(resetToken);
        verifyNoMoreInteractions(passwordResetTokenService);
        verify(userService, times(1)).findByUsername(email);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void sendPasswordResetEmail() {
        String applicationUrl = "https://www.test_url.com";
        String resetUrl = applicationUrl + "/api/password-reset/reset?token=" + token;

        passwordResetService.sendPasswordResetEmail(applicationUrl, email, token);

        verify(emailService).sendPasswordResetEmail(email, resetUrl);
    }
}
