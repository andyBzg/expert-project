package com.example.expertprojectbackend.expert.service.database;

import com.example.expertprojectbackend.expert.dto.ExpertRegistrationDto;
import com.example.expertprojectbackend.expert.entity.Expert;
import jakarta.servlet.http.HttpServletRequest;

public interface ExpertService {

    void registerNewExpert(ExpertRegistrationDto expertDto, HttpServletRequest request);

    void saveExpertVerificationToken(String token, Expert expert);

    void saveExpertToDatabase(ExpertRegistrationDto expertRegistrationDto, String applicationUrl);

}
