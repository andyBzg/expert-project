package com.example.expertprojectbackend.security.config;

import com.example.expertprojectbackend.security.roles.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@RequiredArgsConstructor
public class AdminConfig {

    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    @PostConstruct
    public void createUsersAndAuthorities() {
        if (!userDetailsManager.userExists(adminUsername)) {
            String encodedPassword = passwordEncoder.encode(adminPassword);
            userDetailsManager.createUser(User.withUsername(adminUsername)
                    .password(encodedPassword)
                    .roles(Role.ADMIN.name())
                    .build());
        }
    }
}

