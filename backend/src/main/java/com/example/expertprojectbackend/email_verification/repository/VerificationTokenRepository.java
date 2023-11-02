package com.example.expertprojectbackend.email_verification.repository;

import com.example.expertprojectbackend.email_verification.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findByUsername(String username);

    @Query("SELECT token.id FROM VerificationToken token WHERE token.expirationTime <= :currentTime")
    List<Long> findExpiredTokenIds(Instant currentTime);

    @Query("SELECT token.username FROM VerificationToken token WHERE token.id IN :tokenIds")
    List<String> findExpiredTokenUsernames(List<Long> tokenIds);

    @Modifying
    @Query("DELETE FROM VerificationToken token WHERE token.id IN :tokenIds")
    void deleteTokensByIds(List<Long> tokenIds);

    void deleteAllByRevokedTrue();
}
