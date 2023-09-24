package com.example.expertprojectbackend.password.service.impl;

import com.example.expertprojectbackend.password.dto.PasswordResetDto;
import com.example.expertprojectbackend.password.service.PasswordResetService;
import com.example.expertprojectbackend.password.token.PasswordResetToken;
import com.example.expertprojectbackend.shared.exception.InvalidTokenException;
import com.example.expertprojectbackend.shared.exception.PasswordMismatchException;
import com.example.expertprojectbackend.password.dto.PasswordResetRequestDto;
import com.example.expertprojectbackend.password.service.PasswordResetTokenService;
import com.example.expertprojectbackend.shared.email.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {

    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Override
    public void requestPasswordReset(PasswordResetRequestDto resetRequestDto, HttpServletRequest request) {
        String email = resetRequestDto.email();
        if (!userDetailsManager.userExists(email)) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        PasswordResetToken resetToken = passwordResetTokenService.createPasswordResetToken(email);
        String applicationUrl = getApplicationUrl(request);
        sendPasswordResetEmail(applicationUrl, email, resetToken.getToken());
    }

    @Override
    public void validateResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenService.findByToken(token);
        boolean isTokenValid = passwordResetTokenService.validateToken(resetToken);
        if (!isTokenValid) {
            throw new InvalidTokenException("Token validation failed");
        }

        UserDetails user = userDetailsManager.loadUserByUsername(resetToken.getUsername());
        if (!user.isEnabled()) {
            throw new DisabledException("User is disabled. Forbidden to change password");
        }
    }

    @Override
    public void resetPassword(String token, PasswordResetDto passwordResetDto) {
        validateResetToken(token);

        String password = passwordResetDto.password();
        String passwordConfirmation = passwordResetDto.passwordConfirmation();

        if (!password.equals(passwordConfirmation)) {
            throw new PasswordMismatchException("Incorrect password confirmation");
        }

        PasswordResetToken resetToken = passwordResetTokenService.findByToken(token);
        String username = resetToken.getUsername();

        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        String storedPassword = userDetails.getPassword();

        String encodedNewPassword = passwordEncoder.encode(password);
        userDetailsManager.changePassword(storedPassword, encodedNewPassword);
        log.info("Password changed for user {}", username);
    }

    private String getApplicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private void sendPasswordResetEmail(String applicationUrl, String userEmail, String token) {
        String resetUrl = applicationUrl + "/api/password-reset/reset?token=" + token;
        emailService.sendPasswordResetEmail(userEmail, resetUrl);
    }
}
