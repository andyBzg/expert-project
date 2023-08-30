package com.example.expertprojectbackend.shared.util.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * A utility component for retrieving the current user's session information.
 */
@Component
public class CurrentSessionUtil {

    /**
     * Retrieves the current username from the authentication context.
     *
     * @return The current username.
     * @throws UsernameNotFoundException If the user is not found.
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new UsernameNotFoundException("User is not found.");
    }
}
