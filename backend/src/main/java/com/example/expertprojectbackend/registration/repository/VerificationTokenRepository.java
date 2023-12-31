package com.example.expertprojectbackend.registration.repository;

import com.example.expertprojectbackend.registration.token.VerificationToken;
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

    @Query("SELECT token FROM VerificationToken token WHERE token.user.username = :username")
    Optional<VerificationToken> findByUsername(String username);

    @Query("SELECT token.id FROM VerificationToken token WHERE token.expirationTime <= :currentTime")
    List<Long> findExpiredTokenIds(Instant currentTime);

    @Query("SELECT token.user.username FROM VerificationToken token WHERE token.id IN :tokenIds")
    List<String> findExpiredTokenUsernames(List<Long> tokenIds);

    @Modifying
    @Query("DELETE FROM VerificationToken token WHERE token.id IN :tokenIds")
    void deleteTokensByIds(List<Long> tokenIds);
}
