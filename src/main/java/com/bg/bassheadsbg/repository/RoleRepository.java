package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRole(UserRoleEnum role);
}