package com.example.expertprojectbackend.password.controller;

import com.example.expertprojectbackend.password.service.PasswordChangeService;
import com.example.expertprojectbackend.password.dto.PasswordChangeDto;
import com.example.expertprojectbackend.shared.util.session.CurrentSessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/password-change")
@RequiredArgsConstructor
@Slf4j
public class PasswordChangeController {

    private final PasswordChangeService passwordChangeService;
    private final CurrentSessionUtil currentSessionUtil;

    @PostMapping("/change")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        String currentUsername = currentSessionUtil.getCurrentUsername();
        log.info("Client password change request");
        passwordChangeService.changePassword(currentUsername, passwordChangeDto);
        return ResponseEntity.ok().build();
    }
}
