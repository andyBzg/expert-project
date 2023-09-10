package com.example.expertprojectbackend.client.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@NoArgsConstructor
public class ClientVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant expirationTime;

    private static final int EXPIRATION_TIME_MINUTES = 15;

    @OneToOne
    @JoinColumn(name = "client_uuid")
    private Client client;

    public ClientVerificationToken(String token, Client client) {
        this.token = token;
        this.client = client;
        this.expirationTime = this.calculateTokenExpirationTime();
    }

    public Instant calculateTokenExpirationTime() {
        return Instant.now().plus(EXPIRATION_TIME_MINUTES, ChronoUnit.MINUTES);
    }
}
