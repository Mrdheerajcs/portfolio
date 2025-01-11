package com.auspicius.responce;

import com.auspicius.Entity.Project;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private Integer id;
    private String name;
    private String description;
    private List<String> techStack;
    private String repositoryUrl;
    private String liveDemoUrl;
    private String imageUrl;

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.techStack = project.getTechStack();
        this.repositoryUrl = project.getRepositoryUrl();
        this.liveDemoUrl = project.getLiveDemoUrl();
        this.imageUrl = project.getImageUrl();
    }
}
