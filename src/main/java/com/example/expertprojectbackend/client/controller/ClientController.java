package com.example.expertprojectbackend.client.controller;

import com.example.expertprojectbackend.client.dto.ClientRegistrationDto;
import com.example.expertprojectbackend.client.service.database.ClientService;
import com.example.expertprojectbackend.shared.dto.ChangePasswordDto;
import com.example.expertprojectbackend.shared.service.CredentialsService;
import com.example.expertprojectbackend.shared.util.session.CurrentSessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final CredentialsService credentialsService;
    private final CurrentSessionUtil currentSessionUtil;

    @PostMapping(value = "/registration")
    public ResponseEntity<ClientRegistrationDto> registerExpert(
            @RequestBody ClientRegistrationDto registrationDto,
            final HttpServletRequest request) {
        log.info("New client registration request");
        clientService.registerNewClient(registrationDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationDto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String currentUsername = currentSessionUtil.getCurrentUsername();
        log.info("Client password change request");
        credentialsService.changePassword(currentUsername, changePasswordDto);
        return ResponseEntity.ok().build();
    }
}
