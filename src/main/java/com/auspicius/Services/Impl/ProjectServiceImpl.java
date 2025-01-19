package com.auspicius.Services.Impl;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Project;
import com.auspicius.Repository.PortfolioRepository;
import com.auspicius.Repository.ProjectRepository;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.ProjectService;
import com.auspicius.exception.RecordNotFoundException;
import com.auspicius.helperUtil.Helper;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ProjectDTO;
import com.auspicius.responce.ProjectReq;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public ApiResponse<Project> createProject(ProjectReq projectReq) {
        try {
            validateProjectReq(projectReq);
            Project project = mapToEntity(projectReq);
            Project savedProject = projectRepository.save(project);
            return ResponseUtils.createSuccessResponse(savedProject);
        } catch (IllegalArgumentException e) {
            return ResponseUtils.createFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while creating the project.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<Project> updateProject(Integer id, ProjectReq projectReq) {
        try {
            validateProjectReq(projectReq);
            Project existingProject = projectRepository.findById(id)
                    .orElseThrow(() -> new RecordNotFoundException("Project not found with the provided ID."));
            existingProject.setName(projectReq.getName());
            existingProject.setDescription(projectReq.getDescription());
            existingProject.setStartDate(projectReq.getStartDate());
            existingProject.setEndDate(projectReq.getEndDate());
            existingProject.setStatus(projectReq.getStatus());
            existingProject.setUpdatedOn(Helper.getCurrentTimeStamp());
            Project updatedProject = projectRepository.save(existingProject);
            return ResponseUtils.createSuccessResponse(updatedProject);
        } catch (IllegalArgumentException e) {
            return ResponseUtils.createFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while updating the project.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<Project> updateProjectStatus(Integer id, Boolean status) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Skill with ID " + id + " not found"));
        existingProject.setStatus(status);
        existingProject.setUpdatedOn(Helper.getCurrentTimeStamp());
        Project updatedProject = projectRepository.save(existingProject);
        return ResponseUtils.createSuccessResponse(updatedProject);
    }

    @Override
    public ApiResponse<ProjectDTO> getProjectById(Integer id) {
        return projectRepository.findById(id)
                .map(project -> ResponseUtils.createSuccessResponse(convertToDTO(project)))
                .orElseGet(() -> ResponseUtils.createFailureResponse("Project  not found.", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public ApiResponse<List<ProjectDTO>> getProjectByportfolio(Integer portfolio) {
        try {
            Portfolio portfolios = portfolioRepository.findById(portfolio)
                    .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolio));
            List<Project> project = projectRepository.findByportfolio(portfolios);
            List<ProjectDTO> projectDTOS = project.stream().map(this::convertToDTO).toList();
            return ResponseUtils.createSuccessResponse(projectDTOS);
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse(
                    "An error occurred while retrieving projects.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public ApiResponse<List<ProjectDTO>> getAllProjects() {
        try {
            List<Project> projects = projectRepository.findAll();
            List<ProjectDTO> projectDTOS = projects.stream().map(this::convertToDTO).toList();
            return ResponseUtils.createSuccessResponse(projectDTOS);
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while retrieving projects.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    @Override
    public ApiResponse<String> deleteProject(Integer id) {
        try {
            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new RecordNotFoundException("Project not found."));
            projectRepository.delete(project);
            return ResponseUtils.createSuccessResponse("Project deleted successfully.");
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while deleting the project.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private void validateProjectReq(ProjectReq projectReq) {
        if (projectReq.getUser() == null || !userRepository.existsById(projectReq.getUser())) {
            throw new IllegalArgumentException("Invalid or missing user ID.");
        }
        if (!userRepository.isUserActiveById(projectReq.getUser())) {
            throw new IllegalArgumentException("User is deactivated");
        }
        if (projectReq.getPortfolio() == null || !portfolioRepository.existsById(projectReq.getPortfolio())) {
            throw new IllegalArgumentException("Invalid or missing portfolio ID.");
        }
        if (!portfolioRepository.isPortfolioActiveById(projectReq.getPortfolio())) {
            throw new IllegalArgumentException("Portfolio is deactivated");
        }
        if (projectReq.getName() == null || projectReq.getName().isBlank()) {
            throw new IllegalArgumentException("Project name is required.");
        }
        if (projectReq.getStartDate() == null || projectReq.getStartDate().isBlank()) {
            throw new IllegalArgumentException("Start date is required.");
        }
        if (projectReq.getEndDate() != null && projectReq.getStartDate() != null &&
                projectReq.getEndDate().compareTo(projectReq.getStartDate()) < 0) {
            throw new IllegalArgumentException("End date cannot be before the start date.");
        }
    }

    private Project mapToEntity(ProjectReq projectReq) {
        Project project = new Project();
        project.setName(projectReq.getName());
        project.setDescription(projectReq.getDescription());
        project.setStartDate(projectReq.getStartDate());
        project.setEndDate(projectReq.getEndDate());
        project.setStatus(projectReq.getStatus());
        project.setCreatedOn(Helper.getCurrentTimeStamp());
        project.setUpdatedOn(Helper.getCurrentTimeStamp());
        project.setUser(userRepository.findById(projectReq.getUser())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID.")));
        project.setPortfolio(portfolioRepository.findById(projectReq.getPortfolio())
                .orElseThrow(() -> new IllegalArgumentException("Invalid portfolio ID.")));
        return project;
    }

    private ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(project);
    }
}
