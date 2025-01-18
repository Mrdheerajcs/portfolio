package com.auspicius.responce;

import lombok.Data;

@Data
public class ExperienceDTO {

    private Integer id;
    private String companyName;
    private String role;
    private String startDate;
    private String endDate;
    private String description;
    private String userName;
    private String portfolioTitle;
    private Boolean status;

}

