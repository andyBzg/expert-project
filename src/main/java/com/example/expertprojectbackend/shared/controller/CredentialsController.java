package com.example.expertprojectbackend.shared.controller;

import com.example.expertprojectbackend.shared.dto.ChangePasswordDto;
import com.example.expertprojectbackend.shared.security.service.CredentialsService;
import com.example.expertprojectbackend.shared.util.session.CurrentSessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/credentials")
@RequiredArgsConstructor
@Slf4j
public class CredentialsController {

    private final CredentialsService credentialsService;
    private final CurrentSessionUtil currentSessionUtil;

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String currentUsername = currentSessionUtil.getCurrentUsername();
        log.info("Client password change request");
        credentialsService.changePassword(currentUsername, changePasswordDto);
        return ResponseEntity.ok().build();
    }
}
