package com.auspicius.responce;

public class ApiResponse<T> {
    private T response;
    private Integer status;
    private String message;
    private String salt;
    private boolean isProduction;
    private String key;

    // Getters and Setters
    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isProduction() {
        return isProduction;
    }

    public void setProduction(boolean isProduction) {
        this.isProduction = isProduction;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
