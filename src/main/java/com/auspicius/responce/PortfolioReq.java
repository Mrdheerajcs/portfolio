package com.auspicius.responce;

import com.auspicius.Entity.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PortfolioReq {

    private Integer user;
    private String title; // e.g., "Software Developer Portfolio"
    private String theme; // Optional: Theme or styling preference
    private Boolean isPublic; // Whether the portfolio is public

}
