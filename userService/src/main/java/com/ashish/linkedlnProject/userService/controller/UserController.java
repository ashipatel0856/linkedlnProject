package com.ashish.linkedlnProject.userService.controller;

import com.ashish.linkedlnProject.userService.dto.LoginRequestDto;
import com.ashish.linkedlnProject.userService.dto.SignupRequestDto;
import com.ashish.linkedlnProject.userService.dto.UserDto;
import com.ashish.linkedlnProject.userService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    
    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) {
        UserDto userDto = authService.signUp(signupRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }
}
