package com.example.expertprojectbackend.shared.service;

public interface EmailService {

    void sendRegistrationConfirmationEmail(String email, String verificationUrl);

    void sendPasswordResetEmail(String email, String passwordResetUrl);
}
