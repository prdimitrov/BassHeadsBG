package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.RoleRepository;
import com.bg.bassheadsbg.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UserRole findByName(UserRoleEnum name) {
        return roleRepository.findByRole(name).orElse(null);
    }

    @Override
    public void saveAll(List<UserRole> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    public List<UserRole> findAll() {
        return roleRepository.findAll();
    }
}
