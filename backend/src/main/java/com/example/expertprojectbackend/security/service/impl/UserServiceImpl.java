package com.example.expertprojectbackend.security.service.impl;

import com.example.expertprojectbackend.security.user.User;
import com.example.expertprojectbackend.security.user.UserAuthority;
import com.example.expertprojectbackend.security.repository.UserAuthoritiesRepository;
import com.example.expertprojectbackend.security.repository.UserRepository;
import com.example.expertprojectbackend.security.roles.Role;
import com.example.expertprojectbackend.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserAuthoritiesRepository userAuthoritiesRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${authority.role.prefix}")
    private String rolePrefix;

    @Value("${exception.user.not.found}")
    private String exceptionMessage;

    @Override
    public User registerCredentials(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setEnabled(false);

        UserAuthority authority = new UserAuthority();
        authority.setAuthority(rolePrefix + Role.CLIENT);
        authority.setUser(user);

        userRepository.save(user);
        userAuthoritiesRepository.save(authority);
        log.info("Credentials registered");
        return user;
    }

    @Override
    public void enableUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(exceptionMessage));
        existingUser.setEnabled(true);
        userRepository.save(existingUser);
        log.info("User enabled");
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(exceptionMessage));
    }

    @Override
    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(exceptionMessage));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public void deleteUnverifiedUsers(List<String> usernames) {
        usernames.forEach(userRepository::deleteUserByUsername);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
