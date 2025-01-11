package com.auspicius.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private String title;
    private String phone;
    private String aboutMe;
    private String profilePicture;
    private Boolean status;
    @Column(nullable = false, updatable = false)
    private Timestamp createdOn;
    private Timestamp updatedOn;

    @ElementCollection
    private List<String> socialLinks;
}
