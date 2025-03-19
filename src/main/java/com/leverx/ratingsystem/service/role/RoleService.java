package com.leverx.ratingsystem.service.role;

import com.leverx.ratingsystem.model.user.Role;

public interface RoleService {
    Role getUserRole();

    Role getAdminRole();
}
