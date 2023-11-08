package com.example.expertprojectbackend.email_verification.controller;

import com.example.expertprojectbackend.email_verification.service.EmailVerificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/verify")
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @GetMapping("/email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        log.info("Registration verification request");
        emailVerificationService.verifyRegistration(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resend-verification-email")
    public ResponseEntity<String> resendVerificationEmail(
            @RequestParam("email") String email, final HttpServletRequest request) {
        log.info("New verification email request");
        emailVerificationService.resendVerificationToken(email, request);
        return ResponseEntity.ok("New verification email sent");
    }
}
