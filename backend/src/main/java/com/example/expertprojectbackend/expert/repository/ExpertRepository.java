package com.example.expertprojectbackend.expert.repository;

import com.example.expertprojectbackend.expert.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, UUID> {
}
