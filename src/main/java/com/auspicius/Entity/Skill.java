package com.auspicius.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "Skills")
public class Skill {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private User userId;

    @ManyToOne
    @JsonIgnore
    private Portfolio portfolioId;
    private Boolean status;
    private String name;
    private String level; // Beginner, Intermediate, Expert
    @Column(nullable = false, updatable = false)
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
