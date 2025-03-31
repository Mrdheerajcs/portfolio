package com.auspicius.Controller;

import com.auspicius.Entity.Project;
import com.auspicius.Entity.Skill;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ProjectDTO;
import com.auspicius.responce.ProjectReq;
import com.auspicius.Services.ProjectService;
import com.auspicius.responce.SkillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Project>> createProject(@RequestBody ProjectReq projectReq) {
        ApiResponse<Project> response = projectService.createProject(projectReq);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<ApiResponse<Project>> updateProject(@PathVariable Integer id, @RequestBody ProjectReq projectReq) {
        ApiResponse<Project> response = projectService.updateProject(id, projectReq);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getByPortfolio/{id}")
    public ResponseEntity<ApiResponse<List<ProjectDTO>>> getProjectByportfolio(@PathVariable Integer id) {
        ApiResponse<List<ProjectDTO>> response = projectService.getProjectByportfolio(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<ProjectDTO>> getProjectById(@PathVariable Integer id) {
        ApiResponse<ProjectDTO> response = projectService.getProjectById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ProjectDTO>>> getAllProjects() {
        ApiResponse<List<ProjectDTO>> response = projectService.getAllProjects();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse<Project>> updateProjectStatus(@PathVariable Integer id, @RequestParam Boolean status) {
        ApiResponse<Project> response = projectService.updateProjectStatus(id, status);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @DeleteMapping("/deleteById/{id}")
    public ApiResponse<String> deleteProject(@PathVariable Integer id) {
        return projectService.deleteProject(id);
    }
}
