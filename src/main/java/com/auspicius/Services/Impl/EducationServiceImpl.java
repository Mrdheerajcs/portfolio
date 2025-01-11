package com.auspicius.Services.Impl;

import com.auspicius.Entity.Education;
import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.User;
import com.auspicius.Repository.EducationRepository;
import com.auspicius.Repository.PortfolioRepository;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.EducationService;
import com.auspicius.helperUtil.Helper;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.EducationReq;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public ApiResponse<Education> createEducation(EducationReq educationReq) {
        try {
            // Validate the request data
            validateEducationReq(educationReq);

            // Map the request data to the entity
            Education education = mapToEntity(educationReq);

            // Save the education entity
            Education savedEducation = educationRepository.save(education);

            // Return a success response with the saved education entity
            return ResponseUtils.createSuccessResponse(savedEducation);
        } catch (IllegalArgumentException e) {
            // Handle invalid data (e.g., missing or incorrect fields)
            return ResponseUtils.createFailureResponse(
                    e.getMessage(), HttpStatus.BAD_REQUEST.value()
            );
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseUtils.createFailureResponse(
                    "An error occurred while saving education details.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public ApiResponse<Education> updateEducation(Integer id, EducationReq educationReq) {
        try {
            // Validate the request data
            validateEducationReq(educationReq);

            // Retrieve the existing education record
            Education existingEducation = educationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Education not found with the provided ID."));

            // Map the updated fields from EducationReq to the existing entity
            existingEducation.setBoardName(educationReq.getBoardName());
            existingEducation.setInstitutionName(educationReq.getInstitutionName());
            existingEducation.setDegree(educationReq.getDegree());
            existingEducation.setStartYear(educationReq.getStartYear());
            existingEducation.setEndYear(educationReq.getEndYear());
            existingEducation.setStatus(educationReq.getStatus());
            existingEducation.setDescription(educationReq.getDescription());
            existingEducation.setUpdatedOn(Helper.getCurrentTimeStamp());

            // Save the updated education entity
            Education updatedEducation = educationRepository.save(existingEducation);

            // Return a success response with the updated education entity
            return ResponseUtils.createSuccessResponse(updatedEducation);
        } catch (IllegalArgumentException e) {
            // Handle invalid data (e.g., missing or incorrect fields)
            return ResponseUtils.createFailureResponse(
                    e.getMessage(), HttpStatus.BAD_REQUEST.value()
            );
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseUtils.createFailureResponse(
                    "An error occurred while updating education details.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }


    @Override
    public ApiResponse<Education> getEducationById(Integer id) {
        return educationRepository.findById(id)
                .map(ResponseUtils::createSuccessResponse) // Return the education if found
                .orElseGet(() -> {
                    // Handle the case where education with the given ID is not found
                    return ResponseUtils.createFailureResponse(
                            "Education not found.", HttpStatus.NOT_FOUND.value()
                    );
                });
    }

    @Override
    public ApiResponse<List<Education>> getAllEducations() {
        try {
            // Retrieve all education records
            List<Education> educations = educationRepository.findAll();

            // Return a success response with the list of educations
            return ResponseUtils.createSuccessResponse(educations);
        } catch (Exception e) {
            // Handle any errors during retrieval of education records
            return ResponseUtils.createFailureResponse(
                    "An error occurred while retrieving education records.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public ApiResponse<String> deleteEducation(Integer id) {
        try {
            // Check if the education exists, and delete it if found
            if (educationRepository.existsById(id)) {
                educationRepository.deleteById(id);

                // Return a success response indicating that the education was deleted
                return ResponseUtils.createSuccessResponse("Education deleted successfully.");
            } else {
                // Handle the case where the education with the given ID is not found
                return ResponseUtils.createFailureResponse(
                        "Education not found.", HttpStatus.NOT_FOUND.value()
                );
            }
        } catch (Exception e) {
            // Handle any errors during the deletion of the education record
            return ResponseUtils.createFailureResponse(
                    "An error occurred while deleting the education record.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    // Private method to validate the incoming education request
    private void validateEducationReq(EducationReq educationReq) {
        if (educationReq.getUserId() == null || !userRepository.existsById(educationReq.getUserId())) {
            throw new IllegalArgumentException("Invalid or missing user ID.");
        }
        if (educationReq.getPortfolioId() == null || !portfolioRepository.existsById(educationReq.getPortfolioId())) {
            throw new IllegalArgumentException("Invalid or missing portfolio ID.");
        }
        if (educationReq.getBoardName() == null || educationReq.getBoardName().isBlank()) {
            throw new IllegalArgumentException("Board name is required.");
        }
        if (educationReq.getInstitutionName() == null || educationReq.getInstitutionName().isBlank()) {
            throw new IllegalArgumentException("Institution name is required.");
        }
        if (educationReq.getDegree() == null || educationReq.getDegree().isBlank()) {
            throw new IllegalArgumentException("Degree is required.");
        }
        if (educationReq.getStartYear() <= 0) {
            throw new IllegalArgumentException("Start year must be a positive integer.");
        }
    }

    // Private method to map the EducationReq request to the Education entity
    private Education mapToEntity(EducationReq educationReq) {
        Education education = new Education();

        User user = userRepository.findById(educationReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Portfolio portfolio = portfolioRepository.findById(educationReq.getPortfolioId())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found."));

        education.setUserId(user);
        education.setPortfolioId(portfolio);
        education.setBoardName(educationReq.getBoardName());
        education.setInstitutionName(educationReq.getInstitutionName());
        education.setDegree(educationReq.getDegree());
        education.setStartYear(educationReq.getStartYear());
        education.setEndYear(educationReq.getEndYear());
        education.setStatus(educationReq.getStatus());
        education.setDescription(educationReq.getDescription());
        education.setCreatedOn(Helper.getCurrentTimeStamp());
        education.setUpdatedOn(Helper.getCurrentTimeStamp());

        return education;
    }
}
