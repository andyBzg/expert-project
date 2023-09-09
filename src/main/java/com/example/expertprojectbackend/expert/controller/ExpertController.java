package com.example.expertprojectbackend.expert.controller;

import com.example.expertprojectbackend.shared.dto.ChangePasswordDto;
import com.example.expertprojectbackend.expert.dto.ExpertRegistrationDto;
import com.example.expertprojectbackend.expert.service.database.ExpertDatabaseService;
import com.example.expertprojectbackend.shared.service.CredentialsService;
import com.example.expertprojectbackend.shared.util.session.CurrentSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertDatabaseService expertDatabaseService;
    private final CredentialsService credentialsService;
    private final CurrentSessionUtil currentSessionUtil;

    @PostMapping(value = "/registration")
    public ResponseEntity<ExpertRegistrationDto> registerExpert(@RequestBody ExpertRegistrationDto registrationDto) {
        expertDatabaseService.registerNewExpert(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationDto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String currentUsername = currentSessionUtil.getCurrentUsername();
        credentialsService.changePassword(currentUsername, changePasswordDto);
        return ResponseEntity.ok().build();
    }
}
