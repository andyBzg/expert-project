package com.example.expertprojectbackend.shared.email.impl;

import com.example.expertprojectbackend.shared.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${verification.email.subject}")
    private String verificationSubject;

    @Value("${verification.email.body}")
    private String verificationEmailBody;

    @Value("${password-reset.email.subject}")
    private String passwordResetSubject;

    @Value("${password-reset.email.body}")
    private String passwordResetEmailBody;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendRegistrationConfirmationEmail(String email, String verificationUrl) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(email);
        mailMessage.setSubject(verificationSubject);
        mailMessage.setText(verificationEmailBody + " " + verificationUrl);

        javaMailSender.send(mailMessage);
        log.info("Confirmation email sent");
    }

    @Override
    public void sendPasswordResetEmail(String email, String passwordResetUrl) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(email);
        mailMessage.setSubject(passwordResetSubject);
        mailMessage.setText(passwordResetEmailBody + " " + passwordResetUrl);

        javaMailSender.send(mailMessage);
        log.info("Password reset email sent");
    }
}
