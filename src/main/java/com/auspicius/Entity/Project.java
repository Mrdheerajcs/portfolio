package com.auspicius.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Portfolio portfolio;

    private String name;

    @Column(length = 2000)
    private String description;

    @ElementCollection
    private List<String> techStack;

    private String repositoryUrl;
    private String liveDemoUrl;
    private String imageUrl;
}
