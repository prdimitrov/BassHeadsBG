package com.bg.bassheadsbg.model.entity.users;

import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull UserRoleEnum getRole() {
        return role;
    }

    public void setRole(@NotNull UserRoleEnum role) {
        this.role = role;
    }
}
