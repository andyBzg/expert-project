package com.example.expertprojectbackend.shared.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    String oldPassword;
    String newPassword;
}
