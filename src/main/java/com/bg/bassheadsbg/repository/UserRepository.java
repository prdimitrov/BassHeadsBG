package com.bg.bassheadsbg.repository;

import com.bg.bassheadsbg.model.entity.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(final String email);

    Optional<UserEntity> findByUsername(final String username);
}