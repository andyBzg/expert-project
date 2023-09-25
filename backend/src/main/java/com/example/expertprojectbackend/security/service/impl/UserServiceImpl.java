package com.example.expertprojectbackend.security.service.impl;

import com.example.expertprojectbackend.security.service.UserService;
import com.example.expertprojectbackend.security.database.User;
import com.example.expertprojectbackend.security.database.UserAuthority;
import com.example.expertprojectbackend.security.roles.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Value("${authority.role.prefix}")
    private String rolePrefix;

    @Override
    public void registerCredentials(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(email);
        user.setPassword(encodedPassword);
        user.setEnabled(false);
        userDetailsManager.createUser(user);
        log.info("Credentials registered");
    }

    @Override
    public void enableUserWithRole(User user, Role role) {
        if (!userDetailsManager.userExists(user.getUsername())) {
            throw new UsernameNotFoundException("User not found with username " + user.getUsername());
        }

        User enabledUser = new User();
        enabledUser.setUsername(user.getUsername());
        enabledUser.setPassword(user.getPassword());
        enabledUser.setEnabled(true);

        UserAuthority authority = new UserAuthority();
        authority.setAuthority(rolePrefix + role);
        authority.setUser(user);

        Set<UserAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        enabledUser.setAuthorities(authorities);

        userDetailsManager.updateUser(enabledUser);
        log.info("Enabling user");
    }

    @Override
    public User findByUsername(String username) {
        if (!userDetailsManager.userExists(username)) {
            throw new UsernameNotFoundException("User not found with username " + username);
        }
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(user.isEnabled());
        return user;
    }

    @Override
    public void changePassword(String username, String newPassword) {
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        String storedPassword = userDetails.getPassword();

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        userDetailsManager.changePassword(storedPassword, encodedNewPassword);
    }

    @Override
    public void deleteUser(String username) {
        userDetailsManager.deleteUser(username);
    }

    @Override
    public void deleteUnverifiedUsers(List<String> usernames) {
        for (String username : usernames) {
            userDetailsManager.deleteUser(username);
        }
    }

    @Override
    public boolean userExists(String username) {
        return userDetailsManager.userExists(username);
    }
}
