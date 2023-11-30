package com.example.expertprojectbackend.security.service.impl;

import com.example.expertprojectbackend.security.auth.AuthenticationRequest;
import com.example.expertprojectbackend.security.auth.AuthenticationResponse;
import com.example.expertprojectbackend.security.auth.RegisterRequest;
import com.example.expertprojectbackend.security.repository.TokenRepository;
import com.example.expertprojectbackend.security.service.AuthenticationService;
import com.example.expertprojectbackend.security.service.JwtService;
import com.example.expertprojectbackend.security.service.UserService;
import com.example.expertprojectbackend.security.token.JwtToken;
import com.example.expertprojectbackend.security.token.TokenType;
import com.example.expertprojectbackend.security.user.User;
import com.example.expertprojectbackend.shared.event.UserRegistrationEvent;
import com.example.expertprojectbackend.shared.exception.PasswordMismatchException;
import com.example.expertprojectbackend.shared.exception.UserAlreadyExistsException;
import com.example.expertprojectbackend.shared.validation.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher eventPublisher;
    private final ValidationService validationService;
    private final MessageSource messageSource;


    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest, HttpServletRequest servletRequest) {
        String email = registerRequest.email();
        String password = registerRequest.password();
        String passwordConfirmation = registerRequest.passwordConfirmation();
        String applicationUrl = getApplicationUrl(servletRequest);

        validationService.validateEmail(email);
        validationService.validatePassword(password);

        checkPasswordMismatch(password, passwordConfirmation);
        checkUserExists(email);

        User user = userService.registerCredentials(email, password);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(user, jwtToken);
        publishRegistrationEvent(registerRequest, applicationUrl);
        log.info(messageSource.getMessage(
                "log.registration.success", null, LocaleContextHolder.getLocale()));

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        validationService.validateEmail(request.email());
        validationService.validatePassword(request.password());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userService.findByUsername(request.email());
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            User user = userService.findByUsername(userEmail);

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private String getApplicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private void checkUserExists(String email) {
        if (userService.userExists(email)) {
            String msg = messageSource.getMessage(
                    "exc.username.exists", null, LocaleContextHolder.getLocale());
            log.error(msg);
            throw new UserAlreadyExistsException(msg);
        }
    }

    private void checkPasswordMismatch(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            String msg = messageSource.getMessage(
                    "exc.password.mismatch", null, LocaleContextHolder.getLocale());
            log.error(msg);
            throw new PasswordMismatchException(msg);
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        JwtToken token = JwtToken.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void publishRegistrationEvent(RegisterRequest request, String applicationUrl) {
        User user = new User();
        user.setUsername(request.email());
        eventPublisher.publishEvent(new UserRegistrationEvent(user, applicationUrl));
    }

    private void revokeAllUserTokens(User user) {
        List<JwtToken> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
