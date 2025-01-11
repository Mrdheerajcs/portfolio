package com.auspicius.Services;

import com.auspicius.Entity.Education;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.EducationReq;

import java.util.List;

public interface EducationService {

    ApiResponse<Education> createEducation(EducationReq educationReq);

    ApiResponse<Education> updateEducation(Integer id, EducationReq educationReq);

    ApiResponse<Education> getEducationById(Integer id);

    ApiResponse<List<Education>> getAllEducations();

    ApiResponse<String> deleteEducation(Integer id);
}
