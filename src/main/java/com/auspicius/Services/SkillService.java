package com.auspicius.Services;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Skill;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SkillDTO;
import com.auspicius.responce.SkillReq;

import java.util.List;

public interface SkillService {
    ApiResponse<Skill> createSkill(SkillReq skillReq);

    ApiResponse<Skill> updateSkill(Integer id, SkillReq skillReq);

    ApiResponse<SkillDTO> getSkillById(Integer id);

    ApiResponse<List<SkillDTO>> getSkillByportfolio(Integer portfolio);

    ApiResponse<List<SkillDTO>> getAllSkills();

    ApiResponse<String> deleteSkill(Integer id);

    ApiResponse<Skill> updateSkillStatus(Integer id, Boolean status);

}
