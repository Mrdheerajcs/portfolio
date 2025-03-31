package com.auspicius.responce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private T response;
    private Integer status;
    private String message;
    private String salt;
    private boolean isProduction;
    private String key;


}
