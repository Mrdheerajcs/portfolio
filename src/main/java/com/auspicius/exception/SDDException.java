package com.auspicius.exception;

import lombok.Getter;

@Getter
public class SDDException extends RuntimeException {
    private String field;
    private final Integer status;


    public SDDException(String message, Integer status) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public SDDException(String field, Integer status, String message) {
        super(message);
        this.field = field;
        this.status = status;
    }

    public SDDException(Integer status, String message) {
        super(message);
        this.status = status;
    }
}
