package com.example.expertprojectbackend.security.auth;

public record AuthenticationRequest(
        String email,
        String password
) {
}
