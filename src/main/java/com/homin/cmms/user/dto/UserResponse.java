package com.homin.cmms.user.dto;

import com.homin.cmms.user.User;
import com.homin.cmms.user.UserRole;
import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;

    private String email;

    private String name;

    private UserRole role;

    public UserResponse(Long id, String email, String name, UserRole role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getRole());
    }
}
