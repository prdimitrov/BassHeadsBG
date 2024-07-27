package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.event.InitializationEvent;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.RoleRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AdminInitialization implements CommandLineRunner {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.firstName}")
    private String adminFirstName;

    @Value("${admin.lastName}")
    private String adminLastName;

    @Value("${admin.birthDate}")
    private String adminBirthDate;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AdminInitialization(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEmail(adminEmail);
            admin.setEnabled(true);
            admin.setFirstName(adminFirstName);
            admin.setLastName(adminLastName);
            admin.setBirthDate(LocalDate.parse(adminBirthDate)); // Ensure this format matches the input format!!!
            // yyyy-mm-dd

            Set<UserRole> roles = Set.of(UserRoleEnum.ADMIN, UserRoleEnum.USER).stream()
                    .map(this::getOrCreateUserRole)
                    .collect(Collectors.toSet());

            admin.setRoles(roles);
            userRepository.save(admin);

            eventPublisher.publishEvent(new InitializationEvent(this, "Admin user initialized with username: " + adminUsername));
        }
    }

    private UserRole getOrCreateUserRole(UserRoleEnum roleEnum) {
        return roleRepository.findByRole(roleEnum)
                .orElseGet(() -> {
                    UserRole role = new UserRole();
                    role.setRole(roleEnum);
                    return roleRepository.save(role);
                });
    }
}