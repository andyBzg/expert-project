package com.example.expertprojectbackend.registration.service.impl;

import com.example.expertprojectbackend.client.entity.Client;
import com.example.expertprojectbackend.registration.dto.RegistrationDto;
import com.example.expertprojectbackend.registration.service.VerificationTokenService;
import com.example.expertprojectbackend.registration.token.VerificationToken;
import com.example.expertprojectbackend.registration.event.UserRegistrationEvent;
import com.example.expertprojectbackend.client.service.database.ClientDatabaseService;
import com.example.expertprojectbackend.registration.service.RegistrationService;
import com.example.expertprojectbackend.shared.exception.PasswordMismatchException;
import com.example.expertprojectbackend.shared.exception.TokenNotFoundException;
import com.example.expertprojectbackend.security.database.User;
import com.example.expertprojectbackend.shared.exception.UserAlreadyExistsException;
import com.example.expertprojectbackend.security.roles.Role;
import com.example.expertprojectbackend.security.service.UserService;
import com.example.expertprojectbackend.shared.email.EmailService;
import com.example.expertprojectbackend.shared.exception.UserAlreadyVerifiedException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final ClientDatabaseService clientDatabaseService;
    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher eventPublisher;
    private final EmailService emailService;


    @Override
    @Transactional
    public void registerNewUser(RegistrationDto registrationDto, HttpServletRequest request) {
        String email = registrationDto.email();
        String password = registrationDto.password();
        String passwordConfirmation = registrationDto.passwordConfirmation();
        String applicationUrl = getApplicationUrl(request);

        if (!password.equals(passwordConfirmation)) {
            throw new PasswordMismatchException("Password mismatch");
        }

        if (userService.userExists(email)) {
            throw new UserAlreadyExistsException("Unable to register username, already exists in DB");
        }

        userService.registerCredentials(email, password);
        publishRegistrationEvent(registrationDto, applicationUrl);
        log.info("New user successfully registered and waiting for email verification");
    }

    private void publishRegistrationEvent(RegistrationDto registrationDto, String applicationUrl) {
        User user = new User();
        user.setUsername(registrationDto.email());
        eventPublisher.publishEvent(new UserRegistrationEvent(user, applicationUrl));
    }

    @Override
    @Transactional
    public void verifyRegistration(String token) {
        if (token == null || StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Parameter cannot be null or empty");
        }

        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        String username = verificationToken.getUsername();
        User user = userService.findByUsername(username);

        if (user.isEnabled()) {
            throw new UserAlreadyVerifiedException("This account is already verified");
        }

        validateVerificationToken(verificationToken);
        completeUserRegistration(user);
        verificationTokenService.deleteTokenFromDatabase(verificationToken);
        log.info("User successfully verified with username {}", username);
    }

    private void validateVerificationToken(VerificationToken verificationToken) {
        if (!verificationTokenService.validateToken(verificationToken)) {
            verificationTokenService.deleteTokenFromDatabase(verificationToken);
            userService.deleteUser(verificationToken.getUsername());
            throw new TokenNotFoundException("Token has expired");
        }
    }

    private void completeUserRegistration(User user) {
        userService.enableUserWithRole(user, Role.CLIENT);

        Client client = new Client();
        client.setEmail(user.getUsername());
        clientDatabaseService.saveClientToDatabase(client);
    }

    @Override
    @Transactional
    public void resendVerificationToken(String email, HttpServletRequest request) {
        VerificationToken verificationToken = verificationTokenService.findByUsername(email);
        Instant expirationTime = verificationTokenService.calculateTokenExpirationTime();

        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(expirationTime);

        verificationTokenService.saveTokenToDatabase(verificationToken);
        log.info("Verification token refreshed");
        resendConfirmationEmail(email, getApplicationUrl(request), verificationToken);
    }

    private void resendConfirmationEmail(String email, String applicationUrl, VerificationToken verificationToken) {
        String token = verificationToken.getToken();
        String url = applicationUrl + "/api/register/verifyEmail?token=" + token;
        emailService.sendRegistrationConfirmationEmail(email, url);
    }

    public String getApplicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
