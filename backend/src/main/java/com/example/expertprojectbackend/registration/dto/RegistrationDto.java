package com.example.expertprojectbackend.registration.dto;

public record RegistrationDto(
        String email,
        String password,
        String passwordConfirmation
) {
}
