package com.auspicius.Controller;

import com.auspicius.Entity.Skill;
import com.auspicius.Services.SkillService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SkillReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/create")
    public ApiResponse<Skill> createEducation(@RequestBody SkillReq skillReq) {
        return skillService.createSkill(skillReq);
    }

    @PutMapping("/updateById/{id}")
    public ApiResponse<Skill> updateEducation(@PathVariable Integer id, @RequestBody SkillReq skillReq) {
        return skillService.updateSkill(id, skillReq);
    }

    @GetMapping("/grtById/{id}")
    public ApiResponse<Skill> getEducationById(@PathVariable Integer id) {
        return skillService.getSkillById(id);
    }

    @GetMapping("/getAll")
    public ApiResponse<List<Skill>> getAllEducations() {
        return skillService.getAllSkills();
    }

    @DeleteMapping("/deleteById/{id}")
    public ApiResponse<String> deleteEducation(@PathVariable Integer id) {
        return skillService.deleteSkill(id);
    }
}
