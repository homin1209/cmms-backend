package com.homin.cmms.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    public UserCreateRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
