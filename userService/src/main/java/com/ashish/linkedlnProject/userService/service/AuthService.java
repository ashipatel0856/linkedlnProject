package com.ashish.linkedlnProject.userService.service;

import com.ashish.linkedlnProject.userService.dto.LoginRequestDto;
import com.ashish.linkedlnProject.userService.dto.SignupRequestDto;
import com.ashish.linkedlnProject.userService.dto.UserDto;
import com.ashish.linkedlnProject.userService.entity.User;
import com.ashish.linkedlnProject.userService.exception.BadRequestException;
import com.ashish.linkedlnProject.userService.exception.ResourceNotFoundException;
import com.ashish.linkedlnProject.userService.repository.UserRepository;
import com.ashish.linkedlnProject.userService.utils.BCrypt;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, ModelMapper modelMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    public UserDto signUp(SignupRequestDto signupRequestDto) throws BadRequestException {
        log.info("Signing up a user with email: {}", signupRequestDto.getEmail());

        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(BCrypt.hash(signupRequestDto.getPassword()));
        user = userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {


        log.info("Login a user with email: {}", loginRequestDto.getEmail());
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadRequestException("Incorrect email or password"));

        boolean isPasswordMatch = BCrypt.hash(loginRequestDto.getPassword()).equals(user.getPassword());


        if (isPasswordMatch) {
            throw new BadRequestException("Incorrect email or password");
    }
        return jwtService.generateAccessToken(user);
  }
}
