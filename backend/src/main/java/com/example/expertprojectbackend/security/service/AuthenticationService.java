package com.example.expertprojectbackend.security.service;

import com.example.expertprojectbackend.security.auth.AuthenticationRequest;
import com.example.expertprojectbackend.security.auth.AuthenticationResponse;
import com.example.expertprojectbackend.security.auth.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest, HttpServletRequest servletRequest);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
