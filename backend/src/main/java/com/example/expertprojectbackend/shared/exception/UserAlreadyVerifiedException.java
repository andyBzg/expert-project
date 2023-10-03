package com.example.expertprojectbackend.shared.exception;

public class UserAlreadyVerifiedException extends RuntimeException {
    public UserAlreadyVerifiedException() {
    }

    public UserAlreadyVerifiedException(String message) {
        super(message);
    }
}
