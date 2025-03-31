package com.auspicius.Services;


import com.auspicius.Entity.Project;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ProjectDTO;
import com.auspicius.responce.ProjectReq;

import java.util.List;

public interface ProjectService {


    ApiResponse<Project> createProject(ProjectReq projectReq);


    ApiResponse<Project> updateProject(Integer id, ProjectReq projectReq);

    ApiResponse<Project> updateProjectStatus(Integer id, Boolean status);

    ApiResponse<ProjectDTO> getProjectById(Integer id);

    ApiResponse<List<ProjectDTO>> getProjectByportfolio(Integer portfolio);

    ApiResponse<List<ProjectDTO>> getAllProjects();

    ApiResponse<String> deleteProject(Integer id);
}
