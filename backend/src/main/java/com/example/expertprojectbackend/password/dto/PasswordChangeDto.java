package com.example.expertprojectbackend.password.dto;

public record PasswordChangeDto(
        String oldPassword,
        String newPassword,
        String newPasswordConfirmation
) {
}
