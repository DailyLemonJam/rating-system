package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.auth.AuthRequest;
import com.leverx.ratingsystem.dto.auth.ForgotPasswordRequest;
import com.leverx.ratingsystem.dto.auth.ResetPasswordRequest;
import com.leverx.ratingsystem.dto.auth.VerifyUserEmailRequest;
import com.leverx.ratingsystem.dto.auth.CreateUserRequest;
import com.leverx.ratingsystem.exception.*;
import com.leverx.ratingsystem.model.rating.Rating;
import com.leverx.ratingsystem.model.user.User;
import com.leverx.ratingsystem.model.user.UserEmailStatus;
import com.leverx.ratingsystem.model.user.UserStatus;
import com.leverx.ratingsystem.repository.RatingRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import com.leverx.ratingsystem.util.ConfirmationCodeGenerator;
import com.leverx.ratingsystem.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;
    private final ConfirmationCodeGenerator confirmationCodeGenerator;
    private final ConfirmationCodeService confirmationCodeService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final RoleService roleService;

    @Transactional
    public String createAuthToken(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        }
        catch (BadCredentialsException e) {
            throw new IncorrectUsernameOrPasswordException("Incorrect username or password");
        }
        var user = userRepository.findByName(authRequest.username())
                .orElseThrow(() -> new UserNotFoundException("User not found")); // e.g. could be deleted while having valid token
        if (user.getEmailStatus() == UserEmailStatus.PENDING) {
            throw new UserNotAllowedToLoginException("Email wasn't verified");
        }
        var status = user.getStatus();
        if (status == UserStatus.PENDING) {
            throw new UserNotAllowedToLoginException("Your account wasn't verified by admin yet");
        } else if (status == UserStatus.REJECTED) {
            throw new UserNotAllowedToLoginException("Your account was rejected by admin");
        }
        var userDetails = userService.loadUserByUsername(authRequest.username());
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Transactional
    public void createNewUser(CreateUserRequest createUserRequest) {
        String requestEmail = createUserRequest.email();
        String requestUsername = createUserRequest.username();
        String requestPassword = createUserRequest.password();
        if (userRepository.findByName(requestUsername).isPresent()) {
            throw new UserAlreadyExistsException("User with this name already exists");
        }
        if (userRepository.findByEmail(requestEmail).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        var user = User.builder()
                .name(requestUsername)
                .email(requestEmail)
                .status(UserStatus.PENDING)
                .emailStatus(UserEmailStatus.PENDING)
                .password(passwordEncoder.encode(requestPassword))
                .roles(List.of(roleService.getUserRole()))
                .createdAt(Instant.now())
                .build();
        userRepository.save(user);
        if (ratingRepository.findByUser(user).isEmpty()) {
            var rating = Rating.builder()
                    .user(user)
                    .averageRating(0)
                    .totalRatings(0)
                    .build();
            ratingRepository.save(rating);
        }
        String confirmationCode = confirmationCodeGenerator.generateConfirmationCode();
        confirmationCodeService.save(confirmationCode, requestEmail);
        emailService.sendConfirmationCode(requestEmail,
                "Complete registration",
                confirmationCode);
    }

    @Transactional
    public void verifyUserEmail(VerifyUserEmailRequest verifyUserEmailRequest) {
        String requestEmail = verifyUserEmailRequest.email();
        String requestConfirmationCode = verifyUserEmailRequest.confirmationCode();
        var user = userRepository.findByEmail(requestEmail)
                .orElseThrow(() -> new UserNotFoundException("Can't find user"));
        if (user.getEmailStatus() == UserEmailStatus.VERIFIED) {
            throw new IncorrectVerifyUserEmailRequestException("This email was already verified");
        }
        if (!confirmationCodeService.exists(requestConfirmationCode)) {
            throw new IncorrectVerifyUserEmailRequestException("Incorrect email or confirmation code");
        }
        String value = confirmationCodeService.get(requestConfirmationCode);
        if (value != null && !value.equals(requestEmail)) {
            throw new IncorrectVerifyUserEmailRequestException("Incorrect email");
        }
        confirmationCodeService.delete(requestConfirmationCode);
        user.setEmailStatus(UserEmailStatus.VERIFIED);
        userRepository.save(user);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        String requestEmail = forgotPasswordRequest.email();
        if (!userRepository.existsByEmail(requestEmail)) {
            throw new UserNotFoundException("Can't find user with this email");
        }
        String confirmationCode = confirmationCodeGenerator.generateConfirmationCode();
        confirmationCodeService.save(confirmationCode, requestEmail);
        emailService.sendConfirmationCode(requestEmail,
                "Reset password",
                confirmationCode);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String requestCode = resetPasswordRequest.confirmationCode();
        if (!confirmationCodeService.exists(requestCode)) {
            throw new IncorrectResetPasswordRequestException("Incorrect confirmation code or email");
        }
        String value = confirmationCodeService.get(requestCode);
        String requestEmail = resetPasswordRequest.email();
        if (value != null && !value.equals(requestEmail)) {
            throw new IncorrectResetPasswordRequestException("Incorrect email");
        }
        confirmationCodeService.delete(requestCode);
        String requestPassword = resetPasswordRequest.newPassword();
        var user = userRepository.findByEmail(requestEmail)
                        .orElseThrow(() -> new RuntimeException(""));
        user.setPassword(passwordEncoder.encode(requestPassword));
        userRepository.save(user);
    }

}
