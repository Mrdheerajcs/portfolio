package com.auspicius.responce;

import lombok.Data;

@Data
public class ExperienceReq {

    private Integer user;
    private Integer portfolio;
    private String companyName;
    private String role;
    private String startDate;
    private String endDate;
    private String description;

}
