package com.auspicius.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Portfolios")
public class Portfolio {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private User userId;

    private String title; // e.g., "Software Developer Portfolio"
    private String theme; // Optional: Theme or styling preference
    private Boolean isPublic; // Whether the portfolio is public
    @Column(nullable = false, updatable = false)
    private Timestamp createdOn;
    private Timestamp updatedOn;


    @OneToMany(mappedBy = "portfolioId", cascade = CascadeType.ALL)
    private List<Education> educations;

    @OneToMany(mappedBy = "portfolioId", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "portfolioId", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "portfolioId", cascade = CascadeType.ALL)
    private List<Skill> skills;

    @OneToMany(mappedBy = "portfolioId", cascade = CascadeType.ALL)
    private List<ContactMessage> contactMessages;
}
