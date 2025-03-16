package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.jwt.JwtRequest;
import com.leverx.ratingsystem.dto.user.CreateUserRequest;
import com.leverx.ratingsystem.exception.IncorrectUsernameOrPasswordException;
import com.leverx.ratingsystem.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public String createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        }
        catch (BadCredentialsException e) {
            throw new IncorrectUsernameOrPasswordException("Incorrect username or password");
        }
        // TODO: check if user confirmed email and was verified by admin
        var userDetails = userService.loadUserByUsername(authRequest.username());
        return jwtTokenUtil.generateToken(userDetails);
    }

    public void createNewUser(CreateUserRequest createUserRequest) {
        userService.createNewUser(createUserRequest);
    }

}
