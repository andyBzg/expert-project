package com.example.expertprojectbackend.password.controller;

import com.example.expertprojectbackend.password.service.ChangePasswordService;
import com.example.expertprojectbackend.password.dto.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/change-password")
@RequiredArgsConstructor
@Slf4j
public class ChangePasswordController {

    private final ChangePasswordService changePasswordService;

    @PatchMapping
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) {
        log.info("Client password change request");
        changePasswordService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
