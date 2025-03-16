package com.leverx.ratingsystem.util;

import com.leverx.ratingsystem.config.AppConfiguration;
import com.leverx.ratingsystem.model.game.Game;
import com.leverx.ratingsystem.model.user.Role;
import com.leverx.ratingsystem.repository.GameRepository;
import com.leverx.ratingsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final GameRepository gameRepository;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initGames();
    }

    private void initRoles() {
        var adminRole = Role.builder().name(AppConfiguration.ROLE_ADMIN).build();
        var userRole = Role.builder().name(AppConfiguration.ROLE_USER).build();
        roleRepository.save(adminRole);
        roleRepository.save(userRole);
    }

    private void initGames() {
        var cs2 = Game.builder().name("CS2").build();
        var dota = Game.builder().name("Dota").build();
        var fifa = Game.builder().name("FIFA").build();
        var teamFortress = Game.builder().name("Team Fortress").build();
        gameRepository.save(cs2);
        gameRepository.save(dota);
        gameRepository.save(fifa);
        gameRepository.save(teamFortress);
    }

}
