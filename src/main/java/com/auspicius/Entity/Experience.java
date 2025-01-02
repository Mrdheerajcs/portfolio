package com.auspicius.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Experience {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Portfolio portfolio;

    private String companyName;
    private String role;
    private String startDate; // Format: YYYY-MM-DD
    private String endDate;   // Nullable for ongoing

    @Column(length = 2000)
    private String description;
}
