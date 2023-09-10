package com.example.expertprojectbackend.client.repository;

import com.example.expertprojectbackend.client.entity.ClientVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientVerificationTokenRepository extends JpaRepository<ClientVerificationToken, Long> {
}
