package com.example.expertprojectbackend.security.config;

import com.example.expertprojectbackend.security.user.User;
import com.example.expertprojectbackend.security.user.UserAuthority;
import com.example.expertprojectbackend.security.repository.UserAuthoritiesRepository;
import com.example.expertprojectbackend.security.repository.UserRepository;
import com.example.expertprojectbackend.security.roles.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AdminConfig {

    private final UserRepository userRepository;
    private final UserAuthoritiesRepository userAuthoritiesRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${authority.role.prefix}")
    private String rolePrefix;

    @PostConstruct
    public void createDefaultAdmin() {
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User user = new User();
            user.setUsername(adminUsername);
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setEnabled(true);

            Set<UserAuthority> authorities = Arrays.stream(Role.values())
                    .map(role -> {
                        UserAuthority authority = new UserAuthority();
                        authority.setAuthority(rolePrefix + role);
                        authority.setUser(user);
                        return authority;
                    })
                    .collect(Collectors.toSet());

            user.setAuthorities(authorities);
            userRepository.save(user);
            userAuthoritiesRepository.saveAll(authorities);
        }
    }
}

