package com.auspicius.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "Experiences")
public class Experience {
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
    private String companyName;
    private String role;
    private String startDate; // Format: YYYY-MM-DD
    private String endDate;   // Nullable for ongoing

    @Column(length = 2000)
    private String description;
    @Column(nullable = false, updatable = false)
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
