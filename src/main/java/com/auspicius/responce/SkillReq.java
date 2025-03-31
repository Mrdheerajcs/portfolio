package com.auspicius.responce;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SkillReq {
    private Integer user;
    private Integer portfolio;
    private String name;
    private String level; // Beginner, Intermediate, Expert
}
