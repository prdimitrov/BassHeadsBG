package com.bg.bassheadsbg.init;

import com.bg.bassheadsbg.event.InitializationEvent;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RolesInitializationTest {

    private RoleRepository roleRepository;
    private ApplicationEventPublisher eventPublisher;
    private RolesInitialization rolesInitialization;

    @BeforeEach
    public void setUp() {
        roleRepository = Mockito.mock(RoleRepository.class);
        eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
        rolesInitialization = new RolesInitialization(roleRepository, eventPublisher);
    }

    @Test
    public void testRunRolesInitialization() throws Exception {
        when(roleRepository.count()).thenReturn(0L);
        when(roleRepository.findAll()).thenReturn(
                Arrays.stream(UserRoleEnum.values())
                        .map(roleEnum -> {
                            UserRole role = new UserRole();
                            role.setRole(roleEnum);
                            return role;
                        })
                        .collect(Collectors.toList())
        );

        rolesInitialization.run();

        ArgumentCaptor<UserRole> roleCaptor = ArgumentCaptor.forClass(UserRole.class);
        verify(roleRepository, times(UserRoleEnum.values().length)).save(roleCaptor.capture());

        List<UserRole> savedRoles = roleCaptor.getAllValues();
        List<UserRoleEnum> savedRoleEnums = savedRoles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(UserRoleEnum.values()), savedRoleEnums);

        ArgumentCaptor<InitializationEvent> eventCaptor = ArgumentCaptor.forClass(InitializationEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());

        InitializationEvent publishedEvent = eventCaptor.getValue();
        String expectedEventMessage = "Roles initialized:\n" + Arrays.stream(UserRoleEnum.values())
                .map(UserRoleEnum::toString)
                .collect(Collectors.joining("\n"));

        assertEquals(expectedEventMessage, publishedEvent.getMessage());
    }

    @Test
    public void testRunRolesAlreadyInitialized() throws Exception {
        when(roleRepository.count()).thenReturn(5L);

        rolesInitialization.run();

        verify(roleRepository, never()).save(any(UserRole.class));

        verify(eventPublisher, never()).publishEvent(any(InitializationEvent.class));
    }
}
