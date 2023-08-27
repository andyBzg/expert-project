package com.example.expertprojectbackend.expert.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Expert {
    @Id
    @GeneratedValue
    private Long id;
}
