package com.auspicius.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Education {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Portfolio portfolio;

    private String institutionName;
    private String degree;
    private int startYear;
    private Integer endYear; // Nullable for ongoing education

    @Column(length = 2000)
    private String description;
}


