package com.auspicius.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class SkillDTO {
    private Integer id;
    private Integer userId;
    private Integer portfolioId;
    private String name;
    private String level;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
