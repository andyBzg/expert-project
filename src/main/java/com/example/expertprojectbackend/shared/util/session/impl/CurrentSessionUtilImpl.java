package com.example.expertprojectbackend.shared.util.session.impl;

import com.example.expertprojectbackend.shared.util.session.CurrentSessionUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * A utility component for retrieving the current user's session information.
 */
@Component
public class CurrentSessionUtilImpl implements CurrentSessionUtil {
    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new UsernameNotFoundException("User is not found.");
    }
}
