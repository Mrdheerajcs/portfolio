package com.auspicius.responce;

import lombok.Data;


@Data
public class EducationReq {
    private Integer user;
    private Integer portfolio;
    private String boardName;
    private String institutionName;
    private String degree;
    private int startYear;
    private Integer endYear;
    private Boolean status;
    private String description;
}
