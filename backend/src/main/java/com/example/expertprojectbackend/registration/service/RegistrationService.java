package com.example.expertprojectbackend.registration.service;

import com.example.expertprojectbackend.registration.dto.RegistrationDto;
import jakarta.servlet.http.HttpServletRequest;

public interface RegistrationService {

    void registerNewUser(RegistrationDto registrationDto, HttpServletRequest request);

    void verifyRegistration(String token);

    void resendVerificationToken(String email, HttpServletRequest request);
}
