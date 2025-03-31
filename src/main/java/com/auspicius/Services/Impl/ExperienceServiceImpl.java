package com.auspicius.Services.Impl;

import com.auspicius.Entity.Experience;
import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.User;
import com.auspicius.Repository.ExperienceRepository;
import com.auspicius.Repository.PortfolioRepository;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.ExperienceService;
import com.auspicius.exception.RecordNotFoundException;
import com.auspicius.helperUtil.Helper;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ExperienceDTO;
import com.auspicius.responce.ExperienceReq;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public ApiResponse<Experience> createExperience(ExperienceReq experienceReq) {
        try {
            validateExperienceReq(experienceReq);
            Experience experience = mapToEntity(experienceReq);
            Experience savedExperience = experienceRepository.save(experience);
            return ResponseUtils.createSuccessResponse(savedExperience);
        } catch (IllegalArgumentException e) {
            return ResponseUtils.createFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while creating the experience.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<Experience> updateExperience(Integer id, ExperienceReq experienceReq) {
        try {
            validateExperienceReq(experienceReq);
            Experience existingExperience = experienceRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Experience not found with the provided ID."));
            existingExperience.setCompanyName(experienceReq.getCompanyName());
            existingExperience.setRole(experienceReq.getRole());
            existingExperience.setStartDate(experienceReq.getStartDate());
            existingExperience.setEndDate(experienceReq.getEndDate());
            existingExperience.setDescription(experienceReq.getDescription());
            existingExperience.setUpdatedOn(Helper.getCurrentTimeStamp());
            Experience updatedExperience = experienceRepository.save(existingExperience);
            return ResponseUtils.createSuccessResponse(updatedExperience);
        } catch (IllegalArgumentException e) {
            return ResponseUtils.createFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while updating the experience.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<ExperienceDTO> getExperienceById(Integer id) {
        return experienceRepository.findById(id)
                .map(project -> ResponseUtils.createSuccessResponse(convertToDTO(project)))
                .orElseGet(() -> ResponseUtils.createFailureResponse("Experience  not found.", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public ApiResponse<List<ExperienceDTO>> getExperienceByportfolio(Integer portfolio) {
        try {
            Portfolio portfolios = portfolioRepository.findById(portfolio)
                    .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolio));
            List<Experience> experiences = experienceRepository.findByportfolio(portfolios);
            List<ExperienceDTO> experienceDTOS = experiences.stream().map(this::convertToDTO).toList();
            return ResponseUtils.createSuccessResponse(experienceDTOS);
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse(
                    "An error occurred while retrieving Experience.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public ApiResponse<List<ExperienceDTO>> getAllExperiences() {
        try {
            List<Experience> experiences = experienceRepository.findAll();
            List<ExperienceDTO> experienceDTOS = experiences.stream().map(this::convertToDTO).toList();
            return ResponseUtils.createSuccessResponse(experienceDTOS);
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while retrieving experience.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    @Override
    public ApiResponse<Experience> updateExperienceStatus(Integer id, Boolean status) {
        Experience existingExperience = experienceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Experience with ID " + id + " not found"));
        existingExperience.setStatus(status);
        existingExperience.setUpdatedOn(Helper.getCurrentTimeStamp());
        Experience updatedExperience = experienceRepository.save(existingExperience);
        return ResponseUtils.createSuccessResponse(updatedExperience);
    }

    @Override
    public ApiResponse<String> deleteExperience(Integer id) {
        try {
            Experience experience = experienceRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Experience not found."));
            experienceRepository.delete(experience);
            return ResponseUtils.createSuccessResponse("Experience deleted successfully.");
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while deleting the experience.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private void validateExperienceReq(ExperienceReq experienceReq) {
        if (experienceReq.getUser() == null || !userRepository.existsById(experienceReq.getUser())) {
            throw new IllegalArgumentException("Invalid or missing user ID.");
        }
        if (!userRepository.isUserActiveById(experienceReq.getUser())) {
            throw new IllegalArgumentException("User is deactivated");
        }
        if (experienceReq.getPortfolio() == null || !portfolioRepository.existsById(experienceReq.getPortfolio())) {
            throw new IllegalArgumentException("Invalid or missing portfolio ID.");
        }
        if (!portfolioRepository.isPortfolioActiveById(experienceReq.getPortfolio())) {
            throw new IllegalArgumentException("Portfolio is deactivated");
        }
        if (experienceReq.getCompanyName() == null || experienceReq.getCompanyName().isBlank()) {
            throw new IllegalArgumentException("Company name is required.");
        }
        if (experienceReq.getRole() == null || experienceReq.getRole().isBlank()) {
            throw new IllegalArgumentException("Role is required.");
        }
        if (experienceReq.getStartDate() == null || experienceReq.getStartDate().isBlank()) {
            throw new IllegalArgumentException("Start date is required.");
        }
    }

    private Experience mapToEntity(ExperienceReq experienceReq) {
        Experience experience = new Experience();
        User user = userRepository.findById(experienceReq.getUser())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Portfolio portfolio = portfolioRepository.findById(experienceReq.getPortfolio())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found."));
        experience.setUser(user);
        experience.setPortfolio(portfolio);
        experience.setCompanyName(experienceReq.getCompanyName());
        experience.setRole(experienceReq.getRole());
        experience.setStartDate(experienceReq.getStartDate());
        experience.setEndDate(experienceReq.getEndDate());
        experience.setDescription(experienceReq.getDescription());
        experience.setCreatedOn(Helper.getCurrentTimeStamp());
        experience.setUpdatedOn(Helper.getCurrentTimeStamp());
        experience.setStatus(true);
        return experience;
    }

    private ExperienceDTO convertToDTO(Experience experience) {
        return new ExperienceDTO(experience);
    }
}
