package com.leverx.ratingsystem.service.role;

import com.leverx.ratingsystem.config.AppConfiguration;
import com.leverx.ratingsystem.model.user.Role;
import com.leverx.ratingsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getUserRole() {
        return roleRepository.findByName(AppConfiguration.ROLE_SELLER);
    }

    @Override
    public Role getAdminRole() {
        return roleRepository.findByName(AppConfiguration.ROLE_ADMIN);
    }

}
