package com.auspicius.responce;

import lombok.Data;

@Data
public class ContactMsgReq {
    private Integer user;
    private Integer portfolio;
    private String name;
    private String email;
    private String message;
}
