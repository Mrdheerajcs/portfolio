package com.auspicius.Controller;

import com.auspicius.Entity.Education;
import com.auspicius.Services.EducationService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.EducationReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/educations")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @PostMapping("/create")
    public ApiResponse<Education> createEducation(@RequestBody EducationReq educationReq) {
        return educationService.createEducation(educationReq);
    }

    @PutMapping("/updateById/{id}")
    public ApiResponse<Education> updateEducation(@PathVariable Integer id, @RequestBody EducationReq educationReq) {
        return educationService.updateEducation(id, educationReq);
    }

    @GetMapping("/grtById/{id}")
    public ApiResponse<Education> getEducationById(@PathVariable Integer id) {
        return educationService.getEducationById(id);
    }

    @GetMapping("/getAll")
    public ApiResponse<List<Education>> getAllEducations() {
        return educationService.getAllEducations();
    }

    @DeleteMapping("/deleteById/{id}")
    public ApiResponse<String> deleteEducation(@PathVariable Integer id) {
        return educationService.deleteEducation(id);
    }
}
