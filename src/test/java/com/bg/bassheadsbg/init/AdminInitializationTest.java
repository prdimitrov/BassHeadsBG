package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.event.InitializationEvent;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.RoleRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {
        "admin.username=admin",
        "admin.password=adminpass",
        "admin.email=admin@example.com",
        "admin.firstName=Admin",
        "admin.lastName=User",
        "admin.birthDate=2000-01-01"
})
public class AdminInitializationTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private ApplicationEventPublisher eventPublisher;
    private AdminInitialization adminInitialization;

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

    @BeforeEach
    public void setUp() throws Exception {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        eventPublisher = Mockito.mock(ApplicationEventPublisher.class);

        adminInitialization = new AdminInitialization(userRepository, passwordEncoder, roleRepository, eventPublisher);

        setPrivateField(adminInitialization, "adminUsername", adminUsername);
        setPrivateField(adminInitialization, "adminPassword", adminPassword);
        setPrivateField(adminInitialization, "adminEmail", adminEmail);
        setPrivateField(adminInitialization, "adminFirstName", adminFirstName);
        setPrivateField(adminInitialization, "adminLastName", adminLastName);
        setPrivateField(adminInitialization, "adminBirthDate", adminBirthDate);
    }

    private void setPrivateField(Object target, String fieldName, String value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testRunAdminInitialization() {
        when(userRepository.count()).thenReturn(0L);
        when(passwordEncoder.encode("adminpass")).thenReturn("encodedAdminPass");

        UserRole adminRole = new UserRole();
        adminRole.setRole(UserRoleEnum.ADMIN);
        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);

        when(roleRepository.findByRole(UserRoleEnum.ADMIN)).thenReturn(Optional.empty());
        when(roleRepository.findByRole(UserRoleEnum.USER)).thenReturn(Optional.empty());
        when(roleRepository.save(any(UserRole.class))).thenAnswer(invocation -> invocation.getArgument(0));

        adminInitialization.run();

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());

        UserEntity savedAdmin = userCaptor.getValue();
        assertEquals("admin", savedAdmin.getUsername());
        assertEquals("encodedAdminPass", savedAdmin.getPassword());
        assertEquals("admin@example.com", savedAdmin.getEmail());
        assertEquals("Admin", savedAdmin.getFirstName());
        assertEquals("User", savedAdmin.getLastName());
        assertEquals(LocalDate.of(2000, 1, 1), savedAdmin.getBirthDate());
        assertTrue(savedAdmin.isEnabled());

        Set<UserRoleEnum> expectedRoles = Set.of(UserRoleEnum.ADMIN, UserRoleEnum.USER);
        Set<UserRoleEnum> actualRoles = savedAdmin.getRoles().stream()
                .map(UserRole::getRole)
                .collect(Collectors.toSet());
        assertEquals(expectedRoles, actualRoles);

        ArgumentCaptor<InitializationEvent> eventCaptor = ArgumentCaptor.forClass(InitializationEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        InitializationEvent publishedEvent = eventCaptor.getValue();
        assertEquals("Admin user initialized with username: admin", publishedEvent.getMessage());
    }

    @Test
    public void testRunAdminAlreadyInitialized() {
        when(userRepository.count()).thenReturn(1L);

        adminInitialization.run();

        verify(userRepository, never()).save(any(UserEntity.class));

        verify(eventPublisher, never()).publishEvent(any(InitializationEvent.class));
    }
}
