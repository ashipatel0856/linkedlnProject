package com.ashish.linkedlnProject.userService.service;

import com.ashish.linkedlnProject.userService.dto.LoginRequestDto;
import com.ashish.linkedlnProject.userService.dto.SignupRequestDto;
import com.ashish.linkedlnProject.userService.dto.UserDto;
import com.ashish.linkedlnProject.userService.entity.User;
import com.ashish.linkedlnProject.userService.event.UserCreatedEvent;
import com.ashish.linkedlnProject.userService.exception.BadRequestException;
import com.ashish.linkedlnProject.userService.repository.UserRepository;
import com.ashish.linkedlnProject.userService.utils.BCrypt;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final KafkaTemplate<Long, UserCreatedEvent> userCreatedEventKafkaTemplate;

    public UserDto signUp(SignupRequestDto signupRequestDto) throws BadRequestException {
        log.info("Signing up a user with email: {}", signupRequestDto.getEmail());

        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new BadRequestException("Email has already exists");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(BCrypt.hash(signupRequestDto.getPassword()));

        user = userRepository.save(user);


        // set kafka for notifications
        UserCreatedEvent userCreatedEvent =UserCreatedEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();
        userCreatedEventKafkaTemplate.send("user_created_topic", userCreatedEvent);

        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {

        log.info("Login a user with email: {}", loginRequestDto.getEmail());
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadRequestException("Incorrect email or password, please try again"));

        boolean isPasswordMatch = BCrypt.hash(loginRequestDto.getPassword()).equals(user.getPassword());


        if (isPasswordMatch) {
            throw new BadRequestException("Incorrect email or password");
    }
        return jwtService.generateAccessToken(user);
  }
}
