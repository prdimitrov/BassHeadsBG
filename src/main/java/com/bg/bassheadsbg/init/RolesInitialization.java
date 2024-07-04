package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RolesInitialization implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RolesInitialization(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Arrays.stream(UserRoleEnum.values())
                    .forEach(role -> {
                        UserRole userRole = new UserRole();
                        userRole.setRole(role);
                        roleRepository.save(userRole);
                    });
        }
    }
}
