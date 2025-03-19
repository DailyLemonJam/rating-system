package com.leverx.ratingsystem.service.role;

import com.leverx.ratingsystem.config.AppConfiguration;
import com.leverx.ratingsystem.model.user.Role;
import com.leverx.ratingsystem.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    private Role sellerRole;
    private Role adminRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sellerRole = new Role(1, "ROLE_SELLER");
        adminRole = new Role(2, "ROLE_ADMIN");
    }

    @Test
    void testGetUserRole() {
        when(roleRepository.findByName(AppConfiguration.ROLE_SELLER)).thenReturn(sellerRole);

        Role result = roleServiceImpl.getUserRole();

        assertNotNull(result);
        assertEquals("ROLE_SELLER", result.getName());
        verify(roleRepository).findByName(AppConfiguration.ROLE_SELLER);
    }

    @Test
    void testGetAdminRole() {
        when(roleRepository.findByName(AppConfiguration.ROLE_ADMIN)).thenReturn(adminRole);

        Role result = roleServiceImpl.getAdminRole();

        assertNotNull(result);
        assertEquals("ROLE_ADMIN", result.getName());
        verify(roleRepository).findByName(AppConfiguration.ROLE_ADMIN);
    }
}
