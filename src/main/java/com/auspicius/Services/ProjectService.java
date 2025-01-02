package com.auspicius.Services;


import com.auspicius.Entity.Project;
import com.auspicius.responce.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    ApiResponse<Project> createProject(Project project);

    ApiResponse<Project> updateProject(UUID id, Project project);

    ApiResponse<Void> deleteProject(UUID id);

    ApiResponse<List<Project>> findAllProjects();

    ApiResponse<Project> findProjectById(UUID id);
}
