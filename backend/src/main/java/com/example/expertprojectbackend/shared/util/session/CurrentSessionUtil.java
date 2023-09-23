package com.example.expertprojectbackend.shared.util.session;

/**
 * An interface for retrieving information about the current user's session.
 */
public interface CurrentSessionUtil {

    /**
     * Retrieves the current username from the authentication context.
     *
     * @return The current username.
     */
    String getCurrentUsername();
}
