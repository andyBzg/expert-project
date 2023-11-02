package com.example.expertprojectbackend.password.service;


import com.example.expertprojectbackend.password.dto.ChangePasswordRequest;

import java.security.Principal;

public interface ChangePasswordService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
}
