package com.example.expertprojectbackend.security.repository;

import com.example.expertprojectbackend.security.token.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<JwtToken, Long> {

    Optional<JwtToken> findByToken(String token);

    @Query("SELECT token " +
            "FROM JwtToken token " +
            "INNER JOIN User user ON token.user.id = user.id " +
            "WHERE user.id = :id " +
            "AND (token.expired = false OR token.revoked = false )")
    List<JwtToken> findAllValidTokenByUser(UUID id);
}
