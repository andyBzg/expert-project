package com.example.expertprojectbackend.expert.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpertDto {
    String firstName;
    String lastName;
}
