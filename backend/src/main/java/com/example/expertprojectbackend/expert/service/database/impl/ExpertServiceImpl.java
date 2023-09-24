package com.example.expertprojectbackend.expert.service.database.impl;

import com.example.expertprojectbackend.expert.dto.ExpertRegistrationDto;
import com.example.expertprojectbackend.expert.entity.Expert;
import com.example.expertprojectbackend.expert.repository.ExpertRepository;
import com.example.expertprojectbackend.expert.service.database.ExpertService;
import com.example.expertprojectbackend.expert.repository.ExpertVerificationTokenRepository;
import com.example.expertprojectbackend.expert.entity.ExpertVerificationToken;
import com.example.expertprojectbackend.expert.event.ExpertRegistrationEvent;
import com.example.expertprojectbackend.shared.exception.UserAlreadyExistsException;
import com.example.expertprojectbackend.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpertServiceImpl implements ExpertService {

    private final ExpertRepository expertRepository;
    private final JdbcUserDetailsManager userDetailsManager;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final ExpertVerificationTokenRepository tokenRepository;

    @Override
    @Transactional
    public void registerNewExpert(ExpertRegistrationDto expertRegistrationDto, HttpServletRequest request) {
        String email = expertRegistrationDto.getEmail();
        String password = expertRegistrationDto.getPassword();
        String applicationUrl = getApplicationUrl(request);

        if (userDetailsManager.userExists(email)) {
            throw new UserAlreadyExistsException("Unable to register username, already exists in DB");
        }

        userService.registerCredentials(email, password);
        saveExpertToDatabase(expertRegistrationDto, applicationUrl);
        log.info("New expert successfully registered and waiting for email verification");
    }

    @Override
    public void saveExpertVerificationToken(String token, Expert expert) {
        ExpertVerificationToken expertVerificationToken = new ExpertVerificationToken(token, expert);
        tokenRepository.save(expertVerificationToken);
    }

    @Override
    public void saveExpertToDatabase(ExpertRegistrationDto registrationDto, String applicationUrl) {
        Expert expert = new Expert();
        expert.setFirstName(registrationDto.getFirstName());
        expert.setLastName(registrationDto.getLastName());
        expert.setEmail(registrationDto.getEmail());
        expertRepository.save(expert);
        log.info("New expert saved to DB");
        eventPublisher.publishEvent(new ExpertRegistrationEvent(expert, applicationUrl));
    }

    public String getApplicationUrl(HttpServletRequest request) {
        return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
