package com.example.expertprojectbackend.registration.event;

import com.example.expertprojectbackend.security.database.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {

    private final transient User user;
    private final String applicationUrl;

    public UserRegistrationEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
