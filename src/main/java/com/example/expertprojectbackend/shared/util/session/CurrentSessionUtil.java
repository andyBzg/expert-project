package com.example.expertprojectbackend.shared.util.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentSessionUtil {

    /**
     * Retrieves the current username from the authentication context.
     *
     * @return The current username.
     * @throws RuntimeException If the username is not found in the authentication context.
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new RuntimeException("Username is not found");
    }
}
