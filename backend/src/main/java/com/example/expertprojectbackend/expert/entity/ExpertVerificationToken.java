package com.example.expertprojectbackend.expert.entity;

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
public class ExpertVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant expirationTime;

    private static final int EXPIRATION_TIME_MINUTES = 15;

    @OneToOne
    @JoinColumn(name = "expert_uuid")
    private Expert expert;

    public ExpertVerificationToken(String token, Expert expert) {
        this.token = token;
        this.expert = expert;
        this.expirationTime = this.calculateTokenExpirationTime();
    }

    public Instant calculateTokenExpirationTime() {
        return Instant.now().plus(EXPIRATION_TIME_MINUTES, ChronoUnit.MINUTES);
    }
}
