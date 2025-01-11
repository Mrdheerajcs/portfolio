package com.auspicius.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Projects")
public class Project {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private User userId;

    @ManyToOne
    @JsonIgnore
    private Portfolio portfolioId;

    private String name;

    @Column(length = 2000)
    private String description;

    @ElementCollection
    private List<String> techStack;

    private String repositoryUrl;
    private String liveDemoUrl;
    private String imageUrl;
    @Column(nullable = false, updatable = false)
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
