package com.example.expertprojectbackend.password.service.impl;

import com.example.expertprojectbackend.password.dto.PasswordChangeDto;
import com.example.expertprojectbackend.shared.exception.PasswordMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordChangeServiceImplTest {

    @Mock
    private JdbcUserDetailsManager userDetailsManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordChangeServiceImpl passwordChangeService;

    private String username;
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;

    @BeforeEach
    void setUp() {
        username = "user@test.com";
        oldPassword = "password";
        newPassword = "new_password";
        newPasswordConfirmation = "new_password";
    }

    @Test
    void changePassword_validData_success() {
        // Given
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto(oldPassword, newPassword, newPasswordConfirmation);

        UserDetails userDetails = mock(UserDetails.class);
        String storedPassword = userDetails.getPassword();

        String encodedPassword = "encoded_password";

        when(userDetailsManager.userExists(username)).thenReturn(true);
        when(userDetailsManager.loadUserByUsername(username)).thenReturn(userDetails);
        when(passwordEncoder.matches(oldPassword, storedPassword)).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        // When
        passwordChangeService.changePassword(username, passwordChangeDto);

        // Then
        verify(userDetailsManager, times(1)).userExists(username);
        verify(userDetailsManager, times(1)).loadUserByUsername(username);
        verify(passwordEncoder, times(1)).matches(oldPassword, storedPassword);
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userDetailsManager, times(1)).changePassword(storedPassword, encodedPassword);
    }

    @Test
    void changePassword_incorrectConfirmation_throwsPasswordMismatchException() {
        // Given
        newPasswordConfirmation = "incorrect_password";
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto(oldPassword, newPassword, newPasswordConfirmation);

        // When, Then
        assertThrows(PasswordMismatchException.class, () -> passwordChangeService.changePassword(username, passwordChangeDto));

        verifyNoInteractions(userDetailsManager);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void changePassword_incorrectNewPassword_throwsPasswordMismatchException() {
        // Given
        newPassword = "incorrect_password";
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto(oldPassword, newPassword, newPasswordConfirmation);

        // When, Then
        assertThrows(PasswordMismatchException.class, () -> passwordChangeService.changePassword(username, passwordChangeDto));

        verifyNoInteractions(userDetailsManager);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void changePassword_userNotExist_throwsUsernameNotFoundException() {
        // Given
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto(oldPassword, newPassword, newPasswordConfirmation);

        // When, Then
        assertThrows(UsernameNotFoundException.class, () -> passwordChangeService.changePassword(username, passwordChangeDto));

        verify(userDetailsManager, times(1)).userExists(username);
        verifyNoMoreInteractions(userDetailsManager);
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void changePassword_incorrectOldPassword_throwsBadCredentialsException() {
        // Given
        oldPassword = "incorrect_password";
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto(oldPassword, newPassword, newPasswordConfirmation);

        UserDetails userDetails = mock(UserDetails.class);
        String storedPassword = userDetails.getPassword();

        when(userDetailsManager.userExists(username)).thenReturn(true);
        when(userDetailsManager.loadUserByUsername(username)).thenReturn(userDetails);
        when(passwordEncoder.matches(oldPassword, storedPassword)).thenReturn(false);

        // When, Then
        assertThrows(BadCredentialsException.class, () -> passwordChangeService.changePassword(username, passwordChangeDto));

        verify(userDetailsManager, times(1)).userExists(username);
        verify(userDetailsManager, times(1)).loadUserByUsername(username);
        verify(passwordEncoder, times(1)).matches(oldPassword, storedPassword);
        verifyNoMoreInteractions(passwordEncoder);
        verifyNoMoreInteractions(userDetailsManager);
    }
}
