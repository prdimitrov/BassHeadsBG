package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRole(UserRoleEnum role);
}
