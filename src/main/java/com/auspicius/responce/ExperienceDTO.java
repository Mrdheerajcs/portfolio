package com.auspicius.responce;

import com.auspicius.Entity.Experience;
import com.auspicius.Entity.Project;
import lombok.Data;

@Data
public class ExperienceDTO {

    private Integer id;
    private String companyName;
    private String role;
    private String startDate;
    private String endDate;
    private String description;

    public ExperienceDTO(Experience experience) {
        this.id=experience.getId();
        this.companyName = experience.getCompanyName();
        this.description = experience.getDescription();
        this.role = experience.getRole();
        this.startDate = experience.getStartDate();
        this.endDate = experience.getEndDate();
    }

}

