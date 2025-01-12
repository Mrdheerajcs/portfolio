package com.auspicius.Services.Impl;

import com.auspicius.Entity.Project;
import com.auspicius.Repository.ProjectRepository;
import com.auspicius.Services.ProjectService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ProjectDTO;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ApiResponse<Project> createProject(Project project) {
        try {
            Project savedProject = projectRepository.save(project);
            return ResponseUtils.createSuccessResponse(savedProject);
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse(
                    "An error occurred while saving the project.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public ApiResponse<Project> updateProject(Integer id, Project project) {
        Optional<Project> existingProject = projectRepository.findById(id);
        if (existingProject.isPresent()) {
            Project existing = existingProject.get();

            existing.setName(project.getName());
            existing.setDescription(project.getDescription());
            existing.setTechStack(project.getTechStack()); // Update techStack
            existing.setRepositoryUrl(project.getRepositoryUrl());
            existing.setLiveDemoUrl(project.getLiveDemoUrl());
            existing.setImageUrl(project.getImageUrl());

            Project updatedProject = projectRepository.save(existing);
            return ResponseUtils.createSuccessResponse(updatedProject);
        } else {
            return ResponseUtils.createFailureResponse(
                    "Project not found.",
                    HttpStatus.NOT_FOUND.value()
            );
        }
    }



    @Override
    public ApiResponse<Void> deleteProject(Integer id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return ResponseUtils.createSuccessResponse(null);
        } else {
            return ResponseUtils.createFailureResponse(
                    "Project not found.",
                    HttpStatus.NOT_FOUND.value()
            );
        }
    }

    @Override
    public ApiResponse<List<ProjectDTO>> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectDTO> projectDTOs = projects.stream()
                .map(ProjectDTO::new)
                .collect(Collectors.toList());
        return ResponseUtils.createSuccessResponse(projectDTOs);
    }


    @Override
    public ApiResponse<Project> findProjectById(Integer id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ResponseUtils::createSuccessResponse)
                .orElseGet(() -> ResponseUtils.createFailureResponse(
                        "Project not found.",
                        HttpStatus.NOT_FOUND.value()
                ));
    }
}
