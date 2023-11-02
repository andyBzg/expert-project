package com.example.expertprojectbackend.password.controller;

import com.example.expertprojectbackend.password.dto.ResetPasswordDto;
import com.example.expertprojectbackend.password.dto.ResetPasswordRequest;
import com.example.expertprojectbackend.password.service.PasswordResetService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/password-reset")
@RequiredArgsConstructor
@Slf4j
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordReset(
            @RequestBody ResetPasswordRequest resetRequestDto, final HttpServletRequest request) {
        log.info("Request password reset");
        passwordResetService.requestPasswordReset(resetRequestDto, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateResetToken(@RequestParam("token") String token) {
        log.info("Validating password reset token");
        passwordResetService.validateResetToken(token);
        return ResponseEntity.ok("Token is valid.");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @RequestParam("token") String token, @RequestBody ResetPasswordDto resetPasswordDto) {
        log.info("Resetting password");
        passwordResetService.resetPassword(token, resetPasswordDto);
        return ResponseEntity.ok().build();
    }
}
