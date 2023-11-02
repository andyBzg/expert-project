package com.example.expertprojectbackend.password.service.impl;

import com.example.expertprojectbackend.password.service.ChangePasswordService;
import com.example.expertprojectbackend.security.user.User;
import com.example.expertprojectbackend.security.repository.UserRepository;
import com.example.expertprojectbackend.password.dto.ChangePasswordRequest;
import com.example.expertprojectbackend.shared.exception.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        String newPassword = request.newPassword();
        String confirmationPassword = request.confirmationPassword();

        log.info("new password " + newPassword);
        log.info("new password confirmation " + confirmationPassword);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Wrong password confirmation");
        }

        if (!newPassword.equals(confirmationPassword)) {
            throw new PasswordMismatchException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Password changed for user {}", user.getUsername());
    }
}
