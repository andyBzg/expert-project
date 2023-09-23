package com.example.expertprojectbackend.expert.event;

import com.example.expertprojectbackend.expert.entity.Expert;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ExpertRegistrationEvent extends ApplicationEvent {

    private final transient Expert expert;
    private final String applicationUrl;

    public ExpertRegistrationEvent(Expert expert, String applicationUrl) {
        super(expert);
        this.expert = expert;
        this.applicationUrl = applicationUrl;
    }
}
