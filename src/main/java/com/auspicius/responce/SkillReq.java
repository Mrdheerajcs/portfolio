package com.auspicius.responce;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SkillReq {
    private Integer userId;
    private Integer portfolioId;
    private String name;
    private String level; // Beginner, Intermediate, Expert
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
