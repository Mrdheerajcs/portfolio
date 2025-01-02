package com.auspicius.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class ContactMessage {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Portfolio portfolio;

    @Column(name = "sender_email")
    private String email;

    @Column(length = 2000)
    private String message;
}

