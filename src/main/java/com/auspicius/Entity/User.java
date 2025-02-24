package com.auspicius.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Entity
@Getter
@Setter
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
