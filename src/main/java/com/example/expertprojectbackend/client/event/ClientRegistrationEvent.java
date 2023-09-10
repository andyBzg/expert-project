package com.example.expertprojectbackend.client.event;

import com.example.expertprojectbackend.client.entity.Client;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClientRegistrationEvent extends ApplicationEvent {

    private final transient Client client;
    private final String applicationUrl;

    public ClientRegistrationEvent(Client client, String applicationUrl) {
        super(client);
        this.client = client;
        this.applicationUrl = applicationUrl;
    }
}
