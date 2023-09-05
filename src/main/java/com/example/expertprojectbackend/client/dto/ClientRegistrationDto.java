package com.example.expertprojectbackend.client.dto;

import lombok.Data;

@Data
public class ClientRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
