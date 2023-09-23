package com.example.expertprojectbackend.registration.token;

import com.example.expertprojectbackend.shared.security.database.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@NoArgsConstructor
@Table(name = "verification_tokens")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant expirationTime;

    private static final int EXPIRATION_TIME_MINUTES = 15;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationTime = this.calculateTokenExpirationTime();
    }

    public Instant calculateTokenExpirationTime() {
        return Instant.now().plus(EXPIRATION_TIME_MINUTES, ChronoUnit.MINUTES);
    }
}
