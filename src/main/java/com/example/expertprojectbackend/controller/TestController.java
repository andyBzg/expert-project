package com.example.expertprojectbackend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/test")
public class TestController {

    private final String HOME_VIEW_COUNT = "HOME_VIEW_COUNT";

    @GetMapping(value = "/home")
    public ResponseEntity<String> getGreetingsMsg(Principal principal, HttpSession session) {
        incrementCount(session, HOME_VIEW_COUNT);
        return ResponseEntity.ok("Hello, " + principal.getName());
    }

    @GetMapping("/count")
    public String count(HttpSession session) {
        return "HOME_VIEW_COUNT: " + session.getAttribute(HOME_VIEW_COUNT);
    }

    private void incrementCount(HttpSession session, String attribute) {
        int homeViewCount = session.getAttribute(attribute) == null ? 0 : (Integer) session.getAttribute(attribute);
        session.setAttribute(attribute, homeViewCount += 1);
    }

}
