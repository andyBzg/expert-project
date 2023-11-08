package com.example.expertprojectbackend.password.dto;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword,
        String confirmationPassword
) {
}
