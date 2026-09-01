package com.homin.cmms.user;

import com.homin.cmms.common.exception.DuplicateUserEmailException;
import com.homin.cmms.common.exception.InvalidLoginException;
import com.homin.cmms.user.dto.UserCreateRequest;
import com.homin.cmms.user.dto.UserLoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UserCreateRequest request) {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserEmailException(request.getEmail());
        }

        User user = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getName(), UserRole.USER);

        return userRepository.save(user);
    }

    public User login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidLoginException());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidLoginException();
        }

        return user;
    }
}
