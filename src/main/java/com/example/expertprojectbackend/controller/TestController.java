package com.example.expertprojectbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping(value = "/hello")
    public ResponseEntity<String> getGreetingsMsg() {
        return ResponseEntity.ok("Hello World!");
    }

}
