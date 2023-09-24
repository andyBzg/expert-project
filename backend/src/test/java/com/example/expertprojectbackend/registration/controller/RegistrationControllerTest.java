package com.example.expertprojectbackend.registration.controller;

import com.example.expertprojectbackend.registration.dto.RegistrationDto;
import com.example.expertprojectbackend.registration.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private RegistrationController registrationController;


    @Test
    void registerNewUser_success() {
        // Given
        String email = "test@mail.com";
        String password = "password";
        RegistrationDto registrationDto = new RegistrationDto(email, password);

        // When
        ResponseEntity<RegistrationDto> actual = registrationController.registerNewUser(registrationDto, request);

        // Then
        verify(registrationService, times(1)).registerNewUser(registrationDto, request);
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(registrationDto, actual.getBody());
    }

    @Test
    void verifyEmail_success() {
        // Given
        String token = "testToken";

        // When
        ResponseEntity<String> response = registrationController.verifyEmail(token);

        // Then
        verify(registrationService, times(1)).verifyRegistration(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void resendConfirmationEmail_success() {
        // Given
        String email = "test@example.com";

        // When
        ResponseEntity<String> response = registrationController.resendConfirmationEmail(email, request);

        // Then
        verify(registrationService, times(1)).resendVerificationToken(email, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Confirmation email resent", response.getBody());
    }
}
