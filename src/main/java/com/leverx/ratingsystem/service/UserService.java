package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.dto.auth.ResetPasswordRequest;
import com.leverx.ratingsystem.dto.auth.VerifyUserEmailRequest;
import com.leverx.ratingsystem.dto.user.CreateUserRequest;
import com.leverx.ratingsystem.exception.UserAlreadyExistsException;
import com.leverx.ratingsystem.model.user.User;
import com.leverx.ratingsystem.model.user.UserEmailStatus;
import com.leverx.ratingsystem.model.user.UserStatus;
import com.leverx.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("Can't find user " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles().stream().map(role ->
                        new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    @Transactional
    public void createNewUser(CreateUserRequest createUserRequest) {
        if (findByUsername(createUserRequest.username()).isPresent()) {
            throw new UserAlreadyExistsException("User with this name already exists");
        }
        if (findByEmail(createUserRequest.email()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        var user = User.builder()
                .name(createUserRequest.username())
                .email(createUserRequest.email())
                .status(UserStatus.PENDING)
                .emailStatus(UserEmailStatus.PENDING)
                .password(passwordEncoder.encode(createUserRequest.password()))
                .roles(List.of(roleService.getUserRole()))
                .createdAt(Instant.now())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void resetUserPassword(ResetPasswordRequest resetPasswordRequest) {
        var user = findByEmail(resetPasswordRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user")); // just in case
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.newPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void verifyUserEmail(VerifyUserEmailRequest verifyUserEmailRequest) {
        var user = findByEmail(verifyUserEmailRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user")); // just in case
        user.setEmailStatus(UserEmailStatus.VERIFIED);
        userRepository.save(user);
    }

}
