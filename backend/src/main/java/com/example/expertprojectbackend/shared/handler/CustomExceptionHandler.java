package com.example.expertprojectbackend.shared.handler;

import com.example.expertprojectbackend.shared.exception.InvalidTokenException;
import com.example.expertprojectbackend.shared.exception.PasswordMismatchException;
import com.example.expertprojectbackend.shared.exception.TokenNotFoundException;
import com.example.expertprojectbackend.shared.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException() {
        log.error("User already exists in Database");
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<String> handleTokenNotFoundException() {
        log.error("Token not found");
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidTokenException() {
        log.error("Invalid token or token has expired.");
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> handlePasswordMismatch() {
        log.error("Password and confirmation do not match");
        return ResponseEntity.badRequest().build();
    }
}
