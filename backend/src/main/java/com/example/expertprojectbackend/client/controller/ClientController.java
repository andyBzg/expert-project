package com.example.expertprojectbackend.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    @PostMapping("/update-profile-picture")
    public ResponseEntity<String> updateProfilePicture() {
        // TODO add logging, requestMatchers to security filter chain and service call
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload-document")
    public ResponseEntity<String> uploadDocument() {
        // TODO add logging, requestMatchers to security filter chain and service call
        return ResponseEntity.ok().build();
    }
}
