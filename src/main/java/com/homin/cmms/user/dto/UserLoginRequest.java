package com.homin.cmms.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
