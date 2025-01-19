package com.auspicius.Services.Impl;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Skill;
import com.auspicius.Entity.User;
import com.auspicius.Repository.PortfolioRepository;
import com.auspicius.Repository.SkillRepository;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.SkillService;
import com.auspicius.exception.RecordNotFoundException;
import com.auspicius.helperUtil.Helper;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SkillDTO;
import com.auspicius.responce.SkillReq;
import com.auspicius.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);

    @Override
    public ApiResponse<Skill> createSkill(SkillReq skillReq) {
        try {
            validateSkillReq(skillReq);
            Skill skill = mapToEntity(skillReq);
            Skill savedSkill = skillRepository.save(skill);
            return ResponseUtils.createSuccessResponse(savedSkill);
        } catch (IllegalArgumentException e) {
            return ResponseUtils.createFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while creating the skill.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<Skill> updateSkill(Integer id, SkillReq skillReq) {
        try {
            validateSkillReq(skillReq);
            Skill existingSkill = skillRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Skill not found with the provided ID."));
            existingSkill.setName(skillReq.getName());
            existingSkill.setLevel(skillReq.getLevel());
            existingSkill.setUpdatedOn(Helper.getCurrentTimeStamp());
            Skill updatedSkill = skillRepository.save(existingSkill);
            return ResponseUtils.createSuccessResponse(updatedSkill);
        } catch (IllegalArgumentException e) {
            return ResponseUtils.createFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while updating the skill.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<SkillDTO> getSkillById(Integer id) {
        return skillRepository.findById(id)
                .map(skill -> ResponseUtils.createSuccessResponse(convertToDTO(skill)))
                .orElseGet(() -> ResponseUtils.createFailureResponse("Skill not found.", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public ApiResponse<List<SkillDTO>> getSkillByportfolio(Integer portfolio) {
        try {
            Portfolio portfolios = portfolioRepository.findById(portfolio)
                    .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolio));
            List<Skill> skills = skillRepository.findByportfolio(portfolios);
            List<SkillDTO> skillDTOs = skills.stream().map(this::convertToDTO).toList();
            return ResponseUtils.createSuccessResponse(skillDTOs);
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse(
                    "An error occurred while retrieving skills.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }



    @Override
    public ApiResponse<List<SkillDTO>> getAllSkills() {
        try {
            List<Skill> skills = skillRepository.findAll();
            List<SkillDTO> skillDTOs = skills.stream().map(this::convertToDTO).toList();
            return ResponseUtils.createSuccessResponse(skillDTOs);
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while retrieving skills.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ApiResponse<Skill> updateSkillStatus(Integer id, Boolean status) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Skill with ID " + id + " not found"));
        existingSkill.setStatus(status);
        existingSkill.setUpdatedOn(Helper.getCurrentTimeStamp());
        Skill updatedSkill = skillRepository.save(existingSkill);
        return ResponseUtils.createSuccessResponse(updatedSkill);
    }

    @Override
    public ApiResponse<String> deleteSkill(Integer id) {
        try {
            Skill skill = skillRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Skill not found."));
            skillRepository.delete(skill);
            return ResponseUtils.createSuccessResponse("Skill deleted successfully.");
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while deleting the skill.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private void validateSkillReq(SkillReq skillReq) {
        if (skillReq.getUser() == null || !userRepository.existsById(skillReq.getUser())) {
            throw new IllegalArgumentException("Invalid or missing user ID.");
        }
        if (!userRepository.isUserActiveById(skillReq.getUser())) {
            throw new IllegalArgumentException("User is deactivated");
        }
        if (skillReq.getPortfolio() == null || !portfolioRepository.existsById(skillReq.getPortfolio())) {
            throw new IllegalArgumentException("Invalid or missing portfolio ID.");
        }
        if (!portfolioRepository.isPortfolioActiveById(skillReq.getPortfolio())) {
            throw new IllegalArgumentException("Portfolio is deactivated");
        }
        if (skillReq.getName() == null || skillReq.getName().isBlank()) {
            throw new IllegalArgumentException("Skill name is required.");
        }
        if (skillReq.getLevel() == null || skillReq.getLevel().isBlank()) {
            throw new IllegalArgumentException("Skill level is required.");
        }
    }

    private Skill mapToEntity(SkillReq skillReq) {
        Skill skill = new Skill();
        User user = userRepository.findById(skillReq.getUser())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Portfolio portfolio = portfolioRepository.findById(skillReq.getPortfolio())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found."));
        skill.setUser(user);
        skill.setPortfolio(portfolio);
        skill.setName(skillReq.getName());
        skill.setLevel(skillReq.getLevel());
        skill.setCreatedOn(Helper.getCurrentTimeStamp());
        skill.setUpdatedOn(Helper.getCurrentTimeStamp());
        return skill;
    }

    private SkillDTO convertToDTO(Skill skill) {
        return new SkillDTO(
                skill.getId(),
                skill.getUser() != null ? skill.getUser().getId() : null,
                skill.getPortfolio() != null ? skill.getPortfolio().getId() : null,
                skill.getName(),
                skill.getLevel(),
                skill.getCreatedOn(),
                skill.getUpdatedOn()
        );
    }
}
