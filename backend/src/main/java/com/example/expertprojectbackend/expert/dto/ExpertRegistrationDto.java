package com.example.expertprojectbackend.expert.dto;

import lombok.Data;

@Data
public class ExpertRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
