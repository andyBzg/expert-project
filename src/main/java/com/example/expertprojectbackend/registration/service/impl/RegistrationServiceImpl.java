package com.example.expertprojectbackend.registration.service.impl;

import com.example.expertprojectbackend.registration.dto.RegistrationDto;
import com.example.expertprojectbackend.registration.token.VerificationToken;
import com.example.expertprojectbackend.registration.event.UserRegistrationEvent;
import com.example.expertprojectbackend.registration.repository.VerificationTokenRepository;
import com.example.expertprojectbackend.client.service.database.ClientDatabaseService;
import com.example.expertprojectbackend.registration.service.RegistrationService;
import com.example.expertprojectbackend.shared.exception.TokenNotFoundException;
import com.example.expertprojectbackend.shared.security.database.User;
import com.example.expertprojectbackend.shared.security.database.UserAuthority;
import com.example.expertprojectbackend.shared.exception.UserAlreadyExistsException;
import com.example.expertprojectbackend.shared.security.roles.Role;
import com.example.expertprojectbackend.shared.security.service.CredentialsService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final JdbcUserDetailsManager userDetailsManager;
    private final CredentialsService credentialsService;
    private final ClientDatabaseService clientDatabaseService;
    private final VerificationTokenRepository tokenRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    @Transactional
    public void registerNewUser(RegistrationDto registrationDto, HttpServletRequest request) {
        String email = registrationDto.getEmail();
        String password = registrationDto.getPassword();
        String applicationUrl = getApplicationUrl(request);

        if (userDetailsManager.userExists(email)) {
            throw new UserAlreadyExistsException("Unable to register username, already exists in DB");
        }

        credentialsService.registerCredentials(email, password);
        publishRegistrationEvent(registrationDto, applicationUrl);
        log.info("New user successfully registered and waiting for email verification");
    }

    private void publishRegistrationEvent(RegistrationDto registrationDto, String applicationUrl) {
        User user = new User();
        user.setUsername(registrationDto.getEmail());
        eventPublisher.publishEvent(new UserRegistrationEvent(user, applicationUrl));
    }

    @Override
    public void saveVerificationToken(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public void verifyRegistration(String token) {
        if (token == null || StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Parameter cannot be null or empty");
        }
        Optional<VerificationToken> tokenOptional = tokenRepository.findByToken(token);
        VerificationToken verificationToken = tokenOptional
                .orElseThrow(() -> new TokenNotFoundException("Token not found"));

        if (verificationToken.getUser().isEnabled()) {
            log.info("This account is already verified");
        } else {
            validateToken(verificationToken);
            enableUser(verificationToken);
        }
    }

    private void enableUser(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        String username = user.getUsername();

        if (userDetailsManager.userExists(username)) {
            User updatedUser = new User();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setEnabled(true);

            UserAuthority authority = new UserAuthority();
            authority.setAuthority(ROLE_PREFIX + Role.CLIENT.name());
            authority.setUser(user);

            Set<UserAuthority> authorities = new HashSet<>();
            authorities.add(authority);

            updatedUser.setAuthorities(authorities);

            userDetailsManager.updateUser(updatedUser);
            log.info("Enabling user");
            clientDatabaseService.saveClientToDatabase(updatedUser);
            tokenRepository.delete(verificationToken);
        }
    }

    private void validateToken(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        String username = user.getUsername();

        if (verificationToken.getExpirationTime().isBefore(Instant.now())) {
            tokenRepository.delete(verificationToken);
            credentialsService.deleteUser(username);
            throw new TokenNotFoundException("Token has expired");
        }
    }

    public String getApplicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
