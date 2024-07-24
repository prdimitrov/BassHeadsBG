package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;

import java.util.List;

public interface RoleService {
    UserRole findByName(UserRoleEnum name);

    void saveAll(List<UserRole> roles);

    List<UserRole> findAll();
}
