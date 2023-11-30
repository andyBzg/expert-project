package com.example.expertprojectbackend.shared.validation.impl;

import com.example.expertprojectbackend.shared.exception.InvalidEmailException;
import com.example.expertprojectbackend.shared.exception.InvalidPasswordException;
import com.example.expertprojectbackend.shared.validation.ValidationService;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValidationServiceImpl implements ValidationService {

    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-!\"#$%&()*,:;?@\\\\^_`{|}~+<=>])" +
                    "[a-zA-Z0-9-!\"#$%&()*,:;?@\\\\^_`{|}~+<=>]{8,128}$";


    private final MessageSource messageSource;

    @Override
    public void validateEmail(String email) throws InvalidEmailException {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            String msg = messageSource.getMessage(
                    "exc.invalid.email", null, LocaleContextHolder.getLocale());
            log.error(msg);
            throw new InvalidEmailException(msg + "\n" + ex);
        }
    }

    @Override
    public void validatePassword(String password) throws InvalidPasswordException {
        if (!password.matches(PASSWORD_REGEX)) {
            String msg = messageSource.getMessage(
                    "exc.invalid.password", null, LocaleContextHolder.getLocale());
            log.error(msg);
            throw new InvalidPasswordException(msg);
        }
    }
}
