package com.example.expertprojectbackend.password.controller;

import com.example.expertprojectbackend.password.dto.PasswordChangeDto;
import com.example.expertprojectbackend.password.service.PasswordChangeService;
import com.example.expertprojectbackend.shared.util.session.CurrentSessionUtil;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordChangeControllerTest {

    @Mock
    private PasswordChangeService passwordChangeService;

    @Mock
    private CurrentSessionUtil currentSessionUtil;

    @InjectMocks
    private PasswordChangeController passwordChangeController;


    @Test
    void changePassword_success() {
        // Given
        String username = "test@username.com";
        String oldPassword = "old_password";
        String newPassword = "new_password";
        String newPasswordConfirmation = "new_password";
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto(oldPassword, newPassword, newPasswordConfirmation);

        when(currentSessionUtil.getCurrentUsername()).thenReturn(username);

        // When
        ResponseEntity<String> actual = passwordChangeController.changePassword(passwordChangeDto);

        // Then
        verify(currentSessionUtil, times(1)).getCurrentUsername();
        verify(passwordChangeService, times(1)).changePassword(username, passwordChangeDto);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNull(actual.getBody());
    }
}
