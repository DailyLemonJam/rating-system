package com.leverx.ratingsystem.service.auth;

import com.leverx.ratingsystem.dto.auth.*;

public interface AuthService {
    String createAuthToken(AuthRequest authRequest);

    void createNewUser(CreateUserRequest createUserRequest);

    void verifyUserEmail(VerifyUserEmailRequest verifyUserEmailRequest);

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
