package com.auspicius.responce;

import lombok.Data;
import java.util.List;

@Data
public class ProjectReq {
    private Integer userId;
    private Integer portfolioId;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private Boolean status;
    private List<String> techStack;
    private String repositoryUrl;
    private String liveDemoUrl;
    private String imageUrl;
}
