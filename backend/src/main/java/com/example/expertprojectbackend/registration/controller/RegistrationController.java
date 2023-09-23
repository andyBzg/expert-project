package com.example.expertprojectbackend.registration.controller;

import com.example.expertprojectbackend.registration.dto.RegistrationDto;
import com.example.expertprojectbackend.registration.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/register")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/new-user")
    public ResponseEntity<RegistrationDto> registerNewUser(
            @RequestBody RegistrationDto registrationDto, final HttpServletRequest request) {
        log.info("New client registration request");
        registrationService.registerNewUser(registrationDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationDto);
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        log.info("Registration verification request");
        registrationService.verifyRegistration(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resend-confirmation-email")
    public ResponseEntity<String> resendConfirmationEmail(
            @RequestParam("email") String email, final HttpServletRequest request) {
        log.info("New confirmation email request");
        registrationService.resendVerificationToken(email, request);
        return ResponseEntity.ok("Confirmation email resent");
    }
}
