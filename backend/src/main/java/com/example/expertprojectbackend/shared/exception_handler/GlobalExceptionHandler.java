package com.example.expertprojectbackend.shared.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadValueData() {
        log.error("bad value");
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException() {
        log.error("Incorrect credentials");
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<String> handleDisabledException() {
        log.error("User is disabled");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
