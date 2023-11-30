package com.example.expertprojectbackend.shared.validation;

import com.example.expertprojectbackend.shared.exception.InvalidEmailException;
import com.example.expertprojectbackend.shared.exception.InvalidPasswordException;

public interface ValidationService {
    void validateEmail(String email) throws InvalidEmailException;

    void validatePassword(String password) throws InvalidPasswordException;
}
