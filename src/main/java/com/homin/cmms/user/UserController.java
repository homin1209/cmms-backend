package com.homin.cmms.user;

import com.homin.cmms.user.dto.UserCreateRequest;
import com.homin.cmms.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @Valid @RequestBody UserCreateRequest request
    ) {
        User user = userService.create(request);

        UserResponse userResponse = UserResponse.from(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResponse);
    }
}
