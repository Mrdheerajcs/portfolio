package com.auspicius.responce;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EducationReq {
    private Integer userId;
    private Integer portfolioId;
    private String boardName;
    private String institutionName;
    private String degree;
    private int startYear;
    private Integer endYear;
    private Boolean status;
    private String description;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
