package com.auspicius.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId; // Renamed for clarity

    @Column(nullable = false)
    private String title; // e.g., "Software Developer Portfolio"

    private String theme; // Optional: Theme or styling preference

    @Column(nullable = false)
    private Boolean isPublic; // Whether the portfolio is public

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdOn; // Automatically populated when created

    @UpdateTimestamp
    private Timestamp updatedOn; // Automatically updated on changes

    @Column(nullable = false)
    private Boolean status; // Indicates if the portfolio is active or not

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Education> educations;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Experience> experiences;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Project> projects;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Skill> skills;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ContactMessage> contactMessages;
}
