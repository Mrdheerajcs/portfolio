package com.auspicius.Services;

import com.auspicius.Entity.Skill;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SkillReq;

import java.util.List;

public interface SkillService {
    ApiResponse<Skill> createSkill(SkillReq skillReq);

    ApiResponse<Skill> updateSkill(Integer id, SkillReq skillReq);

    ApiResponse<Skill> getSkillById(Integer id);

    ApiResponse<List<Skill>> getAllSkills();

    ApiResponse<String> deleteSkill(Integer id);
}
