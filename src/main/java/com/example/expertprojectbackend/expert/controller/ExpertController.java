package com.example.expertprojectbackend.expert.controller;

import com.example.expertprojectbackend.expert.dto.ExpertDto;
import com.example.expertprojectbackend.expert.service.database.ExpertDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertDatabaseService expertDatabaseService;

    @PostMapping(value = "/registration")
    public ResponseEntity<ExpertDto> registerExpert(@RequestBody ExpertDto expertDto) {
        expertDatabaseService.create(expertDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(expertDto);
    }
}
