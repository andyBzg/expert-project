package com.example.expertprojectbackend.registration.service.impl;

import com.example.expertprojectbackend.client.entity.Client;
import com.example.expertprojectbackend.client.service.database.ClientDatabaseService;
import com.example.expertprojectbackend.registration.dto.RegistrationDto;
import com.example.expertprojectbackend.registration.event.UserRegistrationEvent;
import com.example.expertprojectbackend.registration.service.VerificationTokenService;
import com.example.expertprojectbackend.registration.token.VerificationToken;
import com.example.expertprojectbackend.security.database.User;
import com.example.expertprojectbackend.security.roles.Role;
import com.example.expertprojectbackend.security.service.UserService;
import com.example.expertprojectbackend.shared.email.EmailService;
import com.example.expertprojectbackend.shared.exception.PasswordMismatchException;
import com.example.expertprojectbackend.shared.exception.TokenNotFoundException;
import com.example.expertprojectbackend.shared.exception.UserAlreadyExistsException;
import com.example.expertprojectbackend.shared.exception.UserAlreadyVerifiedException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private ClientDatabaseService clientDatabaseService;

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private EmailService emailService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Test
    void registerNewUser_validData_success() {
        // Given
        String username = "user@example.com";
        String password = "password";
        String passwordConfirmation = "password";
        RegistrationDto registrationDto = new RegistrationDto(username, password, passwordConfirmation);
        when(userService.userExists(username)).thenReturn(false);

        // When
        registrationService.registerNewUser(registrationDto, httpServletRequest);

        // Then
        verify(userService, times(1)).userExists(username);
        verify(userService, times(1)).registerCredentials(username, password);
        verify(eventPublisher, times(1)).publishEvent(any(UserRegistrationEvent.class));
    }

    @Test
    void registerNewUser_invalidPasswordConfirmation_throwsPasswordMismatchException() {
        // Given
        String username = "user@example.com";
        String password = "password";
        String passwordConfirmation = "invalid_password";
        RegistrationDto registrationDto = new RegistrationDto(username, password, passwordConfirmation);

        // When, Then
        assertThrows(PasswordMismatchException.class, () -> registrationService.registerNewUser(registrationDto, httpServletRequest));

        verifyNoInteractions(userService);
        verifyNoInteractions(eventPublisher);
    }

    @Test
    void registerNewUser_userAlreadyExists_throwsUserAlreadyExistsException() {
        // Given
        String username = "user@example.com";
        String password = "password";
        String passwordConfirmation = "password";
        RegistrationDto registrationDto = new RegistrationDto(username, password, passwordConfirmation);
        when(userService.userExists(username)).thenReturn(true);

        // When, Then
        assertThrows(UserAlreadyExistsException.class, () -> registrationService.registerNewUser(registrationDto, httpServletRequest));

        verify(userService, times(1)).userExists(username);
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(eventPublisher);
    }

    @Test
    void verifyRegistration_validData_success() {
        // Given
        String username = "user@example.com";
        String token = "exampleToken";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUsername(username);
        User user = new User();
        user.setEnabled(false);
        when(verificationTokenService.findByToken(token)).thenReturn(verificationToken);
        when(userService.findByUsername(username)).thenReturn(user);
        when(verificationTokenService.validateToken(verificationToken)).thenReturn(true);

        // When
        registrationService.verifyRegistration(token);

        // Then
        verify(verificationTokenService, times(1)).findByToken(token);
        verify(userService, times(1)).findByUsername(username);
        verify(verificationTokenService, times(1)).validateToken(verificationToken);
        verify(verificationTokenService, times(1)).deleteTokenFromDatabase(verificationToken);
        verify(userService, times(1)).enableUserWithRole(user, Role.CLIENT);
        verify(clientDatabaseService, times(1)).saveClientToDatabase(any(Client.class));
        verifyNoMoreInteractions(verificationTokenService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void verifyRegistration_emptyToken_throwsIllegalArgumentException() {
        // Given
        String token = "";

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> registrationService.verifyRegistration(token));

        verifyNoInteractions(verificationTokenService);
        verifyNoInteractions(userService);
        verifyNoInteractions(clientDatabaseService);
    }

    @Test
    void verifyRegistration_nullToken_throwsIllegalArgumentException() {
        // When, Then
        assertThrows(IllegalArgumentException.class, () -> registrationService.verifyRegistration(null));

        verifyNoInteractions(verificationTokenService);
        verifyNoInteractions(userService);
        verifyNoInteractions(clientDatabaseService);
    }

    @Test
    void verifyRegistration_userIsAlreadyVerified_throwsUserAlreadyVerifiedException() {
        // Given
        String username = "user@example.com";
        String token = "exampleToken";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUsername(username);
        User user = new User();
        user.setEnabled(true);
        when(verificationTokenService.findByToken(token)).thenReturn(verificationToken);
        when(userService.findByUsername(username)).thenReturn(user);

        // When, Then
        assertThrows(UserAlreadyVerifiedException.class, () -> registrationService.verifyRegistration(token));

        verify(verificationTokenService, times(1)).findByToken(token);
        verify(userService, times(1)).findByUsername(username);
        verifyNoInteractions(clientDatabaseService);
        verifyNoMoreInteractions(verificationTokenService);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void verifyRegistration_invalidToken_deletesInvalidTokenAndUnverifiedUser_throwsTokenNotFoundException() {
        // Given
        String username = "user@example.com";
        String token = "exampleToken";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUsername(username);
        User user = new User();
        user.setEnabled(false);
        when(verificationTokenService.findByToken(token)).thenReturn(verificationToken);
        when(userService.findByUsername(username)).thenReturn(user);
        when(verificationTokenService.validateToken(verificationToken)).thenReturn(false);

        // When, Then
        assertThrows(TokenNotFoundException.class, () -> registrationService.verifyRegistration(token));

        verify(verificationTokenService, times(1)).findByToken(token);
        verify(userService, times(1)).findByUsername(username);
        verify(verificationTokenService, times(1)).validateToken(verificationToken);
        verify(verificationTokenService, times(1)).deleteTokenFromDatabase(verificationToken);
        verify(userService, times(1)).deleteUser(verificationToken.getUsername());
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(clientDatabaseService);
        verifyNoMoreInteractions(verificationTokenService);
    }

    @Test
    void resendVerificationToken() {
        // Given
        String email = "test@example.com";
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.MINUTES);
        VerificationToken verificationToken = new VerificationToken();

        when(verificationTokenService.findByUsername(email)).thenReturn(verificationToken);
        when(verificationTokenService.calculateTokenExpirationTime()).thenReturn(expirationTime);

        // When
        registrationService.resendVerificationToken(email, httpServletRequest);

        // Then
        verify(verificationTokenService, times(1)).findByUsername(email);
        verify(verificationTokenService, times(1)).calculateTokenExpirationTime();
        verify(verificationTokenService, times(1)).saveTokenToDatabase(verificationToken);
        verify(emailService, times(1)).sendRegistrationConfirmationEmail(anyString(), anyString());
    }
}
