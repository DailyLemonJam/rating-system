package com.leverx.ratingsystem.util;

import com.leverx.ratingsystem.config.AppConfiguration;
import com.leverx.ratingsystem.model.game.Game;
import com.leverx.ratingsystem.model.user.Role;
import com.leverx.ratingsystem.model.user.User;
import com.leverx.ratingsystem.model.user.UserEmailStatus;
import com.leverx.ratingsystem.model.user.UserStatus;
import com.leverx.ratingsystem.repository.GameRepository;
import com.leverx.ratingsystem.repository.RoleRepository;
import com.leverx.ratingsystem.repository.UserRepository;
import com.leverx.ratingsystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final GameRepository gameRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRepository userRepository;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        initRoles();
        initGames();
        initAdmins();
    }

    private void initRoles() {
        var adminRole = Role.builder().name(AppConfiguration.ROLE_ADMIN).build();
        var userRole = Role.builder().name(AppConfiguration.ROLE_SELLER).build();
        roleRepository.save(adminRole);
        roleRepository.save(userRole);
    }

    private void initGames() {
        for (var available_game : AppConfiguration.AVAILABLE_GAMES) {
            var game = Game.builder().name(available_game).build();
            gameRepository.save(game);
        }
    }

    private void initAdmins() {
        var admin = User.builder()
                .name(adminName)
                .email(adminEmail)
                .status(UserStatus.APPROVED)
                .emailStatus(UserEmailStatus.VERIFIED)
                .password(passwordEncoder.encode(adminPassword))
                .roles(List.of(roleService.getAdminRole()))
                .createdAt(Instant.now())
                .build();
        userRepository.save(admin);
    }

}
