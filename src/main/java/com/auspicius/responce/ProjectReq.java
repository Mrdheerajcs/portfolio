package com.auspicius.responce;

import lombok.Data;

@Data
public class ProjectReq {
    private Integer user;
    private Integer portfolio;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private Boolean status;

    // Getters and Setters
}