package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.jwt.JwtRequest;
import com.leverx.ratingsystem.dto.jwt.JwtResponse;
import com.leverx.ratingsystem.dto.user.CreateUserRequest;
import com.leverx.ratingsystem.dto.user.CreateUserResponse;
import com.leverx.ratingsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createAuthToken(@Valid @RequestBody JwtRequest authRequest) {
        String token = authService.createAuthToken(authRequest);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> createNewUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        authService.createNewUser(createUserRequest);
        return new ResponseEntity<>(new CreateUserResponse("Confirmation code was sent to your email address"), HttpStatus.OK);
    }

    // TODO: reset password feature

}
