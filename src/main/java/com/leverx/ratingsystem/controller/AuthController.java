package com.leverx.ratingsystem.controller;

import com.leverx.ratingsystem.dto.auth.*;
import com.leverx.ratingsystem.dto.auth.CreateUserRequest;
import com.leverx.ratingsystem.dto.auth.CreateUserResponse;
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
    public ResponseEntity<AuthResponse> createAuthToken(@Valid @RequestBody AuthRequest authRequest) {
        String token = authService.createAuthToken(authRequest);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> createNewUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        authService.createNewUser(createUserRequest);
        return new ResponseEntity<>(new CreateUserResponse("Confirmation code was sent to your email address"), HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyUserEmailResponse> verifyNewUserEmail(@Valid @RequestBody VerifyUserEmailRequest verifyUserEmailRequest) {
        authService.verifyUserEmail(verifyUserEmailRequest);
        return new ResponseEntity<>(new VerifyUserEmailResponse("Your email was successfully verified"), HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.forgotPassword(forgotPasswordRequest);
        return new ResponseEntity<>(new ForgotPasswordResponse("Reset code was sent to your email"), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);
        return new ResponseEntity<>(new ResetPasswordResponse("Password was successfully changed"), HttpStatus.OK);
    }

}
