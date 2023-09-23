package com.example.expertprojectbackend.expert.repository;

import com.example.expertprojectbackend.expert.entity.ExpertVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertVerificationTokenRepository extends JpaRepository<ExpertVerificationToken, Long> {
}
