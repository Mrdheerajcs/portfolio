package com.auspicius.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "ContactMessages")
public class ContactMessage {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private Portfolio portfolioId;

    @ManyToOne
    @JsonIgnore
    private User userId;

    @Column(name = "sender_email")
    private String email;

    @Column(length = 2000)
    private String message;

    @Column(nullable = false, updatable = false)
    private Timestamp createdOn;
    private Timestamp updatedOn;
}

