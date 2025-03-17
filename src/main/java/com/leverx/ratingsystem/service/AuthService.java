package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.auth.AuthRequest;
import com.leverx.ratingsystem.dto.auth.ForgotPasswordRequest;
import com.leverx.ratingsystem.dto.auth.ResetPasswordRequest;
import com.leverx.ratingsystem.dto.auth.VerifyUserEmailRequest;
import com.leverx.ratingsystem.dto.user.CreateUserRequest;
import com.leverx.ratingsystem.exception.IncorrectResetPasswordRequestException;
import com.leverx.ratingsystem.exception.IncorrectUsernameOrPasswordException;
import com.leverx.ratingsystem.exception.IncorrectVerifyUserEmailRequestException;
import com.leverx.ratingsystem.exception.UserNotAllowedToLoginException;
import com.leverx.ratingsystem.model.user.UserEmailStatus;
import com.leverx.ratingsystem.model.user.UserStatus;
import com.leverx.ratingsystem.util.ConfirmationCodeGenerator;
import com.leverx.ratingsystem.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final ConfirmationCodeGenerator confirmationCodeGenerator;
    private final ConfirmationCodeService confirmationCodeService;
    private final EmailService emailService;

    @Transactional
    public String createAuthToken(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        }
        catch (BadCredentialsException e) {
            throw new IncorrectUsernameOrPasswordException("Incorrect username or password");
        }
        var user = userService.findByUsername(authRequest.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); // e.g. could be deleted while having valid token
        if (user.getEmailStatus() == UserEmailStatus.PENDING) {
            throw new UserNotAllowedToLoginException("Email wasn't verified");
        }
        var status = user.getStatus();
        if (status == UserStatus.PENDING) {
            throw new UserNotAllowedToLoginException("Your account wasn't verified by admin yet");
        } else if (status == UserStatus.REJECTED) {
            throw new UserNotAllowedToLoginException("Your account creation was rejected by admin");
        }
        var userDetails = userService.loadUserByUsername(authRequest.username());
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Transactional
    public void createNewUser(CreateUserRequest createUserRequest) {
        userService.createNewUser(createUserRequest);
        String requestEmail = createUserRequest.email();
        String confirmationCode = confirmationCodeGenerator.generateConfirmationCode();
        confirmationCodeService.save(confirmationCode, requestEmail);
        emailService.sendConfirmationCode(requestEmail,
                "Complete registration",
                confirmationCode);
    }

    @Transactional
    public void verifyUserEmail(VerifyUserEmailRequest verifyUserEmailRequest) {
        var user = userService.findByEmail(verifyUserEmailRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user"));
        if (user.getEmailStatus() == UserEmailStatus.VERIFIED) {
            throw new IncorrectVerifyUserEmailRequestException("This email is already verified");
        }
        String requestCode = verifyUserEmailRequest.confirmationCode();
        if (!confirmationCodeService.exists(requestCode)) {
            throw new IncorrectVerifyUserEmailRequestException("Incorrect email or confirmation code");
        }
        String value = confirmationCodeService.get(requestCode);
        String requestEmail = verifyUserEmailRequest.email();
        if (!value.equals(requestEmail)) {
            throw new IncorrectVerifyUserEmailRequestException("Incorrect email or confirmation code");
        }
        confirmationCodeService.delete(requestCode);
        userService.verifyUserEmail(verifyUserEmailRequest);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        userService.findByEmail(forgotPasswordRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("User with this email doesn't exist"));
        String confirmationCode = confirmationCodeGenerator.generateConfirmationCode();
        String requestEmail = forgotPasswordRequest.email();
        confirmationCodeService.save(confirmationCode, requestEmail);
        emailService.sendConfirmationCode(requestEmail,
                "Reset password",
                confirmationCode);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String requestCode = resetPasswordRequest.confirmationCode();
        if (!confirmationCodeService.exists(requestCode)) {
            throw new IncorrectResetPasswordRequestException("Confirmation code or email is incorrect");
        }
        String value = confirmationCodeService.get(requestCode);
        String requestEmail = resetPasswordRequest.email();
        if (!value.equals(requestEmail)) {
            throw new IncorrectResetPasswordRequestException("Confirmation code or email is incorrect");
        }
        confirmationCodeService.delete(requestCode);
        userService.resetUserPassword(resetPasswordRequest);
    }

}
