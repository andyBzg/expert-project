package com.example.expertprojectbackend.email_verification.service;

import jakarta.servlet.http.HttpServletRequest;

public interface EmailVerificationService {

    void verifyRegistration(String token);

    void resendVerificationToken(String email, HttpServletRequest request);
}
