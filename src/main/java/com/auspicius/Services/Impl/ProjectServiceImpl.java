package com.auspicius.Services.Impl;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Project;
import com.auspicius.Entity.User;
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

import java.sql.Timestamp;
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
        User user = userRepository.findById(projectReq.getUserId())
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        Portfolio portfolio = portfolioRepository.findById(projectReq.getPortfolioId())
                .orElseThrow(() -> new RecordNotFoundException("Portfolio not found"));

        Project project = new Project();
        project.setUser(user);
        project.setPortfolio(portfolio);
        project.setName(projectReq.getName());
        project.setDescription(projectReq.getDescription());
        project.setStartDate(projectReq.getStartDate());
        project.setEndDate(projectReq.getEndDate());
        project.setStatus(projectReq.getStatus());
        project.setTechStack(projectReq.getTechStack());
        project.setRepositoryUrl(projectReq.getRepositoryUrl());
        project.setLiveDemoUrl(projectReq.getLiveDemoUrl());
        project.setImageUrl(projectReq.getImageUrl());
        project.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        project.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

        Project savedProject = projectRepository.save(project);
        return ResponseUtils.createSuccessResponse(savedProject);
    }

    @Override
    public ApiResponse<Project> updateProject(Integer id, ProjectReq projectReq) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Project not found"));

        project.setName(projectReq.getName());
        project.setDescription(projectReq.getDescription());
        project.setStartDate(projectReq.getStartDate());
        project.setEndDate(projectReq.getEndDate());
        project.setStatus(projectReq.getStatus());
        project.setTechStack(projectReq.getTechStack());
        project.setRepositoryUrl(projectReq.getRepositoryUrl());
        project.setLiveDemoUrl(projectReq.getLiveDemoUrl());
        project.setImageUrl(projectReq.getImageUrl());
        project.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

        Project updatedProject = projectRepository.save(project);
        return ResponseUtils.createSuccessResponse(updatedProject);
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
        if (projectReq.getUserId() == null || !userRepository.existsById(projectReq.getUserId())) {
            throw new IllegalArgumentException("Invalid or missing user ID.");
        }
        if (!userRepository.isUserActiveById(projectReq.getUserId())) {
            throw new IllegalArgumentException("User is deactivated");
        }
        if (projectReq.getPortfolioId()== null || !portfolioRepository.existsById(projectReq.getPortfolioId())) {
            throw new IllegalArgumentException("Invalid or missing portfolio ID.");
        }
        if (!portfolioRepository.isPortfolioActiveById(projectReq.getPortfolioId())) {
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
        project.setUser(userRepository.findById(projectReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID.")));
        project.setPortfolio(portfolioRepository.findById(projectReq.getPortfolioId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid portfolio ID.")));
        return project;
    }

    private ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(project);
    }
}
