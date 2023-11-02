package com.example.expertprojectbackend.security.repository;

import com.example.expertprojectbackend.security.user.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthoritiesRepository extends JpaRepository<UserAuthority, Long> {
}
