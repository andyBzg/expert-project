package com.example.expertprojectbackend.expert.service.database.impl;

import com.example.expertprojectbackend.expert.dto.ExpertRegistrationDto;
import com.example.expertprojectbackend.expert.entity.Expert;
import com.example.expertprojectbackend.expert.repository.ExpertRepository;
import com.example.expertprojectbackend.expert.service.database.ExpertDatabaseService;
import com.example.expertprojectbackend.security.roles.Role;
import com.example.expertprojectbackend.shared.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpertDatabaseServiceImpl implements ExpertDatabaseService {

    private final ExpertRepository expertRepository;
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerNewExpert(ExpertRegistrationDto expertRegistrationDto) {
        String email = expertRegistrationDto.getEmail();
        String encodedPassword = passwordEncoder.encode(expertRegistrationDto.getPassword());

        if (userDetailsManager.userExists(email)) {
            throw new UserAlreadyExistsException("Unable to register username, already exists in DB");
        }

        registerCredentials(email, encodedPassword);
        saveExpertToDatabase(expertRegistrationDto);
        log.info("new expert successfully registered");
    }

    private void saveExpertToDatabase(ExpertRegistrationDto expertRegistrationDto) {
        Expert expert = new Expert();
        expert.setFirstName(expertRegistrationDto.getFirstName());
        expert.setLastName(expertRegistrationDto.getLastName());
        expert.setEmail(expertRegistrationDto.getEmail());
        expertRepository.save(expert);
        log.info("new expert saved to DB");
    }

    private void registerCredentials(String email, String encodedPassword) {
        userDetailsManager.createUser(User.withUsername(email)
                .password(encodedPassword)
                .roles(Role.EXPERT.name())
                .build());
        log.info("credentials registered");
    }
}
