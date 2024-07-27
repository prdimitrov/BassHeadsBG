package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByName() {
        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.ADMIN);

        when(roleRepository.findByRole(UserRoleEnum.ADMIN)).thenReturn(Optional.of(userRole));

        UserRole result = roleService.findByName(UserRoleEnum.ADMIN);

        assertEquals(userRole, result);
    }

    @Test
    public void testFindByName_NotFound() {
        when(roleRepository.findByRole(UserRoleEnum.USER)).thenReturn(Optional.empty());

        UserRole result = roleService.findByName(UserRoleEnum.USER);

        assertNull(result);
    }

    @Test
    public void testSaveAll() {
        UserRole role1 = new UserRole();
        UserRole role2 = new UserRole();
        List<UserRole> roles = List.of(role1, role2);

        roleService.saveAll(roles);

        verify(roleRepository).saveAll(eq(roles));
    }

    @Test
    public void testFindAll() {
        UserRole role1 = new UserRole();
        UserRole role2 = new UserRole();
        List<UserRole> roles = List.of(role1, role2);

        when(roleRepository.findAll()).thenReturn(roles);

        List<UserRole> result = roleService.findAll();

        assertEquals(roles, result);
    }
}
