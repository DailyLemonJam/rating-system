package com.leverx.ratingsystem.util;

import com.leverx.ratingsystem.config.AppConfiguration;
import com.leverx.ratingsystem.model.user.Role;
import com.leverx.ratingsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
    }

    private void initRoles() {
        var adminRole = Role.builder()
                .name(AppConfiguration.ROLE_ADMIN).build();
        roleRepository.save(adminRole);
        var userRole = Role.builder()
                .name(AppConfiguration.ROLE_USER).build();
        roleRepository.save(userRole);
    }

}
