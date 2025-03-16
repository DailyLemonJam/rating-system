package com.leverx.ratingsystem.service;

import com.leverx.ratingsystem.model.user.Role;
import com.leverx.ratingsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER");
    }

    public Role getAdminRole() {
        return roleRepository.findByName("ROLE_ADMIN");
    }

}
