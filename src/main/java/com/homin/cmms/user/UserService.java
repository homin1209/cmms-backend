package com.homin.cmms.user;

import com.homin.cmms.common.exception.DuplicateUserEmailException;
import com.homin.cmms.user.dto.UserCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(UserCreateRequest request) {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserEmailException(request.getEmail());
        }

        User user = new User(request.getEmail(), request.getPassword(), request.getName(), UserRole.USER);

        return userRepository.save(user);
    }
}
