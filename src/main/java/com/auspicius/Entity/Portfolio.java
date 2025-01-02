package com.auspicius.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Portfolio {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Education> educations;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Skill> skills;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<ContactMessage> contactMessages;

    private String title; // e.g., "Software Developer Portfolio"
    private String theme; // Optional: Theme or styling preference
    private Boolean isPublic; // Whether the portfolio is public


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<ContactMessage> getContactMessages() {
        return contactMessages;
    }

    public void setContactMessages(List<ContactMessage> contactMessages) {
        this.contactMessages = contactMessages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
