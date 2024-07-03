package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.users.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

}
