package com.auspicius.Controller;

import com.auspicius.Entity.Skill;
import com.auspicius.Services.SkillService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SkillReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/create")
    public ApiResponse<Skill> createSkill(@RequestBody SkillReq skillReq) {
        return skillService.createSkill(skillReq);
    }

    @PutMapping("/updateById/{id}")
    public ApiResponse<Skill> updateSkill(@PathVariable Integer id, @RequestBody SkillReq skillReq) {
        return skillService.updateSkill(id, skillReq);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse<Skill> getSkillById(@PathVariable Integer id) {
        return skillService.getSkillById(id);
    }

    @GetMapping("/getAll")
    public ApiResponse<List<Skill>> getAllSkills() {
        return skillService.getAllSkills();
    }

    @DeleteMapping("/deleteById/{id}")
    public ApiResponse<String> deleteSkill(@PathVariable Integer id) {
        return skillService.deleteSkill(id);
    }

}
