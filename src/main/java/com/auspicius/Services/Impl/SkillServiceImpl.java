package com.auspicius.Services.Impl;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Skill;
import com.auspicius.Entity.User;
import com.auspicius.Repository.PortfolioRepository;
import com.auspicius.Repository.SkillRepository;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.SkillService;
import com.auspicius.helperUtil.Helper;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SkillReq;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public ApiResponse<Skill> createSkill(SkillReq skillReq) {
        try {
            validateSkillReq(skillReq);

            Skill skill = mapToEntity(skillReq);

            Skill savedSkill = skillRepository.save(skill);

            // Return a success response with the saved education entity
            return ResponseUtils.createSuccessResponse(savedSkill);
        } catch (IllegalArgumentException e) {
            // Handle invalid data (e.g., missing or incorrect fields)
            return ResponseUtils.createFailureResponse(
                    e.getMessage(), HttpStatus.BAD_REQUEST.value()
            );
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseUtils.createFailureResponse(
                    "An error occurred while saving skill details.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public ApiResponse<Skill> updateSkill(Integer id, SkillReq skillReq) {
        try {
            // Validate the request data
            validateSkillReq(skillReq);

            // Retrieve the existing education record
            Skill existingSkill = skillRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Skill not found with the provided ID."));

            // Map the updated fields from EducationReq to the existing entity
            existingSkill.setName(skillReq.getName());
            existingSkill.setLevel(skillReq.getLevel());
            existingSkill.setUpdatedOn(Helper.getCurrentTimeStamp());

            Skill updatedSkill = skillRepository.save(existingSkill);

            // Return a success response with the updated education entity
            return ResponseUtils.createSuccessResponse(updatedSkill);
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
    public ApiResponse<Skill> getSkillById(Integer id) {
        return skillRepository.findById(id)
                .map(ResponseUtils::createSuccessResponse) // Return the education if found
                .orElseGet(() -> {
                    // Handle the case where education with the given ID is not found
                    return ResponseUtils.createFailureResponse(
                            "Education not found.", HttpStatus.NOT_FOUND.value()
                    );
                });
    }

    @Override
    public ApiResponse<List<Skill>> getAllSkills() {
        try {
            // Retrieve all education records
            List<Skill> educations = skillRepository.findAll();

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
    public ApiResponse<String> deleteSkill(Integer id) {
        try {
            // Check if the education exists, and delete it if found
            if (skillRepository.existsById(id)) {
                skillRepository.deleteById(id);

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

    private void validateSkillReq(SkillReq skillReq) {
        if (skillReq.getUserId() == null || !userRepository.existsById(skillReq.getUserId())) {
            throw new IllegalArgumentException("Invalid or missing user ID.");
        }
        if (skillReq.getPortfolioId() == null || !portfolioRepository.existsById(skillReq.getPortfolioId())) {
            throw new IllegalArgumentException("Invalid or missing portfolio ID.");
        }
        if (skillReq.getName() == null || skillReq.getName().isBlank()) {
            throw new IllegalArgumentException("Skill name is required.");
        }
        if (skillReq.getLevel() == null || skillReq.getLevel().isBlank()) {
            throw new IllegalArgumentException("Level is required.");
        }
    }

    private Skill mapToEntity(SkillReq skillReq) {
        Skill skill = new Skill();

        User user = userRepository.findById(skillReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Portfolio portfolio = portfolioRepository.findById(skillReq.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found."));

        skill.setUserId(user);
        skill.setPortfolioId(portfolio);
        skill.setName(skillReq.getName());
        skill.setLevel(skillReq.getLevel());

        return skill;
    }
}
