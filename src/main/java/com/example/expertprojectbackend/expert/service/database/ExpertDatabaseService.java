package com.example.expertprojectbackend.expert.service.database;

import com.example.expertprojectbackend.expert.dto.ExpertRegistrationDto;

public interface ExpertDatabaseService {

    void registerNewExpert(ExpertRegistrationDto expertDto);

}
