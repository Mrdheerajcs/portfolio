package com.auspicius.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "Educations")
public class Education {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private User userId;

    @ManyToOne
    @JsonIgnore
    private Portfolio portfolioId;

    private String boardName;
    private String institutionName;
    private String degree;
    private int startYear;
    private Integer endYear;
    private Boolean status;


    @Column(length = 2000)
    private String description;
    @Column(nullable = false, updatable = false)
    private Timestamp createdOn;
    private Timestamp updatedOn;
}


