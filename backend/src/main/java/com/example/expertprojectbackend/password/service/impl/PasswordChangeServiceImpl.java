package com.example.expertprojectbackend.password.service.impl;

import com.example.expertprojectbackend.password.service.PasswordChangeService;
import com.example.expertprojectbackend.password.dto.PasswordChangeDto;
import com.example.expertprojectbackend.shared.exception.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordChangeServiceImpl implements PasswordChangeService {

    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(String username, PasswordChangeDto passwordChangeDto) {
        String oldPassword = passwordChangeDto.oldPassword();
        String newPassword = passwordChangeDto.newPassword();
        String newPasswordConfirmation = passwordChangeDto.newPasswordConfirmation();

        if (!newPassword.equals(newPasswordConfirmation)) {
            throw new PasswordMismatchException("Incorrect password confirmation");
        }
        if (!userDetailsManager.userExists(username)) {
            throw new UsernameNotFoundException("User not found with username " + username);
        }

        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        String storedPassword = userDetails.getPassword();

        if (!passwordEncoder.matches(oldPassword, storedPassword)) {
            throw new BadCredentialsException("Incorrect old password");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        userDetailsManager.changePassword(storedPassword, encodedNewPassword);
        log.info("Password changed for user {}", username);
    }
}
