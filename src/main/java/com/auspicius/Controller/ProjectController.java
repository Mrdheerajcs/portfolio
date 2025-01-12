package com.auspicius.Controller;

import com.auspicius.Entity.Project;
import com.auspicius.Services.ProjectService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ProjectDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Project>> createProject(@RequestBody @Valid Project project) {
        ApiResponse<Project> response = projectService.createProject(project);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/updateBy/{id}")
    public ResponseEntity<ApiResponse<Project>> updateProject(
            @PathVariable Integer id,
            @RequestBody @Valid Project project) {
        ApiResponse<Project> response = projectService.updateProject(id, project);
        return new ResponseEntity<>(response, response.getStatus() == HttpStatus.OK.value() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("deleteBy/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Integer id) {
        ApiResponse<Void> response = projectService.deleteProject(id);
        return new ResponseEntity<>(response, response.getStatus() == HttpStatus.OK.value() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ProjectDTO>>> findAllProjects() {
        ApiResponse<List<ProjectDTO>> response = projectService.findAllProjects();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/findById/{id}")
    public ResponseEntity<ApiResponse<Project>> findProjectById(@PathVariable Integer id) {
        ApiResponse<Project> response = projectService.findProjectById(id);
        return new ResponseEntity<>(response, response.getStatus() == HttpStatus.OK.value() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }



}
