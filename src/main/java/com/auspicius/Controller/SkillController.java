package com.auspicius.Controller;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.Skill;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SkillDTO;
import com.auspicius.responce.SkillReq;
import com.auspicius.Services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Skill>> createSkill(@RequestBody SkillReq skillReq) {
        ApiResponse<Skill> response = skillService.createSkill(skillReq);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<ApiResponse<Skill>> updateSkill(@PathVariable Integer id, @RequestBody SkillReq skillReq) {
        ApiResponse<Skill> response = skillService.updateSkill(id, skillReq);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getByPortfolio/{id}")
    public ResponseEntity<ApiResponse<List<SkillDTO>>> getSkillsByPortfolioId(@PathVariable Integer id) {
        ApiResponse<List<SkillDTO>> response = skillService.getSkillByPortfolioId(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<SkillDTO>> getSkillById(@PathVariable Integer id) {
        ApiResponse<SkillDTO> response = skillService.getSkillById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<SkillDTO>>> getAllSkills() {
        ApiResponse<List<SkillDTO>> response = skillService.getAllSkills();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse<Skill>> updateSkillStatus(@PathVariable Integer id, @RequestParam Boolean status) {
        ApiResponse<Skill> response = skillService.updateSkillStatus(id, status);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/deleteById/{id}")
    public ApiResponse<String> deleteSkill(@PathVariable Integer id) {
        return skillService.deleteSkill(id);
    }
}
