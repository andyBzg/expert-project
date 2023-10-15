package com.example.expertprojectbackend.listener;

import com.example.expertprojectbackend.registration.event.UserRegistrationEvent;
import com.example.expertprojectbackend.registration.service.VerificationTokenService;
import com.example.expertprojectbackend.registration.token.VerificationToken;
import com.example.expertprojectbackend.security.database.User;
import com.example.expertprojectbackend.shared.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationListenerTest {

    @Mock
    private EmailService emailService;

    @Mock
    private VerificationTokenService verificationTokenService;

    @InjectMocks
    private RegistrationListener registrationListener;


    @Test
    void onApplicationEvent_success() {
        // Given
        User user = new User();
        user.setUsername("user@test.com");
        String applicationUrl = "https://www.test_url.com";
        UserRegistrationEvent event = new UserRegistrationEvent(user, applicationUrl);

        VerificationToken verificationToken = new VerificationToken();
        when(verificationTokenService.createVerificationToken(user.getUsername())).thenReturn(verificationToken);

        // When
        registrationListener.onApplicationEvent(event);

        // Then
        verify(verificationTokenService, times(1)).createVerificationToken(user.getUsername());
        verify(emailService, times(1))
                .sendRegistrationConfirmationEmail(
                        user.getUsername(),
                        event.getApplicationUrl() + "/api/register/verifyEmail?token=" + verificationToken);
    }
}
