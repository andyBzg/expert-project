package com.example.expertprojectbackend.password.dto;

public record PasswordResetDto(
        String password,
        String passwordConfirmation
) {
}
