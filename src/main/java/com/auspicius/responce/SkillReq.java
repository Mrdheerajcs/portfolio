package com.auspicius.responce;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillReq {
    private Integer userId;
    private Integer portfolioId;
    private String name;
    private String level; // Beginner, Intermediate, Expert
}
