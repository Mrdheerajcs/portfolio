package com.auspicius.responce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private String message;
    private Integer code;
    private String details;
    private String field; // Add this field
}
