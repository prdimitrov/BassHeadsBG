package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.event.InitializationEvent;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RolesInitialization implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;
    public RolesInitialization(RoleRepository roleRepository, ApplicationEventPublisher eventPublisher) {
        this.roleRepository = roleRepository;
        this.eventPublisher = eventPublisher;
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

            List<UserRole> roles = roleRepository.findAll();
            String roleDetails = roles.stream()
                            .map(role -> role.getRole().toString())
                                    .collect(Collectors.joining("\n"));

            eventPublisher.publishEvent(new InitializationEvent(this, "Roles initialized:\n" + roleDetails));
        }
    }
}
