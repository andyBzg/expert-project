package com.example.expertprojectbackend.security.auth;

public record RegisterRequest(
        String email,
        String password,
        String passwordConfirmation
) {
}
