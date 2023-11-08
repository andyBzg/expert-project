package com.example.expertprojectbackend.password.dto;

public record ResetPasswordDto(
        String password,
        String confirmationPassword
) {
}
