package com.example.expertprojectbackend.registration.service;

import com.example.expertprojectbackend.registration.dto.RegistrationDto;
import com.example.expertprojectbackend.shared.security.database.User;
import jakarta.servlet.http.HttpServletRequest;

public interface RegistrationService {

    void registerNewUser(RegistrationDto registrationDto, HttpServletRequest request);

    void saveVerificationToken(String token, User user);

    void verifyRegistration(String token);

    void resendVerificationToken(String email, HttpServletRequest request);
}
