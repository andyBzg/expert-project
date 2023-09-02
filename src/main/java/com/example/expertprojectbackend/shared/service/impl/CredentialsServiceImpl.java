package com.example.expertprojectbackend.shared.service.impl;

import com.example.expertprojectbackend.shared.dto.ChangePasswordDto;
import com.example.expertprojectbackend.shared.service.CredentialsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CredentialsServiceImpl implements CredentialsService {

    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(String currentUsername, ChangePasswordDto changePasswordDto) {
        String oldPasswordFromDto = changePasswordDto.getOldPassword();
        String newPasswordFromDto = changePasswordDto.getNewPassword();

        UserDetails userDetails = userDetailsManager.loadUserByUsername(currentUsername);
        String storedPassword = userDetails.getPassword();

        if (!passwordEncoder.matches(oldPasswordFromDto, storedPassword)) {
            throw new BadCredentialsException("Incorrect old password");
        }
        String encodedNewPassword = passwordEncoder.encode(newPasswordFromDto);
        userDetailsManager.changePassword(storedPassword, encodedNewPassword);
        log.info("Password changed for user {}", currentUsername);
    }
}
