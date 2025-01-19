package com.auspicius.Services;

import com.auspicius.Entity.Experience;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ExperienceDTO;
import com.auspicius.responce.ExperienceReq;

import java.util.List;

public interface   ExperienceService {


    ApiResponse<Experience> createExperience(ExperienceReq experienceReq);

    ApiResponse<Experience> updateExperience(Integer id, ExperienceReq experienceReq);

    ApiResponse<ExperienceDTO> getExperienceById(Integer id);

    ApiResponse<List<ExperienceDTO>> getExperienceByportfolio(Integer portfolio);

    ApiResponse<List<ExperienceDTO>> getAllExperiences();

    ApiResponse<Experience> updateExperienceStatus(Integer id, Boolean status);

    ApiResponse<String> deleteExperience(Integer id);
}
