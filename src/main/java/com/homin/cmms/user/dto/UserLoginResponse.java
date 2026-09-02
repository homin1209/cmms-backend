package com.homin.cmms.user.dto;

import lombok.Getter;

@Getter
public class UserLoginResponse {

    private String token;

    public UserLoginResponse(String token) {
        this.token = token;
    }
}
