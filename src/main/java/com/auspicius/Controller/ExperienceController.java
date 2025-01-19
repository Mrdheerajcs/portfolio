package com.auspicius.Controller;

import com.auspicius.Entity.Experience;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ExperienceDTO;
import com.auspicius.responce.ExperienceReq;
import com.auspicius.Services.ExperienceService;
import com.auspicius.responce.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiences")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Experience>> createExperience(@RequestBody ExperienceReq experienceReq) {
        ApiResponse<Experience> response = experienceService.createExperience(experienceReq);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<ApiResponse<Experience>> updateExperience(@PathVariable Integer id, @RequestBody ExperienceReq experienceReq) {
        ApiResponse<Experience> response = experienceService.updateExperience(id, experienceReq);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getByPortfolio/{id}")
    public ResponseEntity<ApiResponse<List<ExperienceDTO>>> getExperienceByportfolio(@PathVariable Integer id) {
        ApiResponse<List<ExperienceDTO>> response = experienceService.getExperienceByportfolio(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<ExperienceDTO>> getExperienceById(@PathVariable Integer id) {
        ApiResponse<ExperienceDTO> response = experienceService.getExperienceById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ExperienceDTO>>> getAllExperiences() {
        ApiResponse<List<ExperienceDTO>> response = experienceService.getAllExperiences();
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse<Experience>> updateExperienceStatus(@PathVariable Integer id, @RequestParam Boolean status) {
        ApiResponse<Experience> response = experienceService.updateExperienceStatus(id, status);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/deleteById/{id}")
    public ApiResponse<String> deleteExperience(@PathVariable Integer id) {
        return experienceService.deleteExperience(id);
    }
}
