package com.ashish.linkedlnProject.userService.service;

import com.ashish.linkedlnProject.userService.dto.SignupRequestDto;
import com.ashish.linkedlnProject.userService.dto.UserDto;
import com.ashish.linkedlnProject.userService.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service

public class AuthService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(AuthService.class);


    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto signUp(SignupRequestDto signupRequestDto) {
        return null;
    }
}
