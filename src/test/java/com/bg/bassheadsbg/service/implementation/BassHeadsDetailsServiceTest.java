package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.details.BassHeadsUserDetails;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BassHeadsDetailsServiceTest {

    private static final String TEST_EMAIL = "ancho@abv.bg";
    private static final String TEST_USERNAME = "DASKAL";
    private static final String NOT_EXISTENT_USERNAME = "NOuser";
    private static final String USER_WITH_NO_ROLES = "norolesuserLOOSER";
    private static final String USER_WITH_MULTIPLE_ROLES = "multiroleuserNOTLOOSER";

    private BassHeadsDetailsService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new BassHeadsDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        UserEntity testUser = new UserEntity();
        testUser.setEmail(TEST_EMAIL);
        testUser.setUsername(TEST_USERNAME);
        testUser.setFirstName("Sani");
        testUser.setLastName("Sanev");
        testUser.setPassword("1234softunibg");
        testUser.setEnabled(true);
        testUser.setBirthDate(LocalDate.of(1997, 12, 15));
        UserRole adminRole = new UserRole();
        adminRole.setRole(UserRoleEnum.ADMIN);
        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);
        testUser.setRoles(Set.of(userRole, adminRole));

        when(mockUserRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = toTest.loadUserByUsername(TEST_USERNAME);

        assertInstanceOf(BassHeadsUserDetails.class, userDetails);

        assertEquals(TEST_USERNAME, userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());

        Set<String> expectedRoles = new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN"));
        Set<String> actualRoles = new HashSet<>(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(mockUserRepository.findByUsername(NOT_EXISTENT_USERNAME))
                .thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(NOT_EXISTENT_USERNAME),
                "Expected loadUserByUsername() to throw, but it didn't"
        );

        assertEquals("User with username " + NOT_EXISTENT_USERNAME + " not found!", thrown.getMessage());
    }

    @Test
    void testLoadUserByUsername_UserWithNoRoles() {
        UserEntity testUser = new UserEntity();
        testUser.setEmail(TEST_EMAIL);
        testUser.setUsername(USER_WITH_NO_ROLES);
        testUser.setFirstName("Sani");
        testUser.setLastName("Sanev");
        testUser.setPassword("1234softunibg");
        testUser.setEnabled(true);
        testUser.setBirthDate(LocalDate.of(1997, 12, 15));
        testUser.setRoles(Set.of());

        when(mockUserRepository.findByUsername(USER_WITH_NO_ROLES))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = toTest.loadUserByUsername(USER_WITH_NO_ROLES);

        assertInstanceOf(BassHeadsUserDetails.class, userDetails);

        assertEquals(USER_WITH_NO_ROLES, userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());

        Set<String> expectedRoles = new HashSet<>();
        Set<String> actualRoles = new HashSet<>(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testLoadUserByUsername_UserWithMultipleRoles() {
        UserEntity testUser = new UserEntity();
        testUser.setEmail(TEST_EMAIL);
        testUser.setUsername(USER_WITH_MULTIPLE_ROLES);
        testUser.setFirstName("Sani");
        testUser.setLastName("Sanev");
        testUser.setPassword("1234softunibg");
        testUser.setEnabled(true);
        testUser.setBirthDate(LocalDate.of(1997, 12, 15));
        UserRole adminRole = new UserRole();
        adminRole.setRole(UserRoleEnum.ADMIN);
        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);
        testUser.setRoles(Set.of(userRole, adminRole));

        when(mockUserRepository.findByUsername(USER_WITH_MULTIPLE_ROLES))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = toTest.loadUserByUsername(USER_WITH_MULTIPLE_ROLES);

        assertInstanceOf(BassHeadsUserDetails.class, userDetails);

        assertEquals(USER_WITH_MULTIPLE_ROLES, userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());

        Set<String> expectedRoles = new HashSet<>(Set.of("ROLE_USER", "ROLE_ADMIN"));
        Set<String> actualRoles = new HashSet<>(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testLoadUserByUsername_UserEntityIsNull() {
        when(mockUserRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(TEST_USERNAME),
                "Expected loadUserByUsername() to throw, but it didn't"
        );

        assertEquals("User with username " + TEST_USERNAME + " not found!", thrown.getMessage());
    }

    @Test
    void testLoadUserByUsername_UserWithSingleRole() {
        UserEntity testUser = new UserEntity();
        testUser.setEmail(TEST_EMAIL);
        testUser.setUsername("SINGLE_ROLE_USER");
        testUser.setFirstName("Sani");
        testUser.setLastName("Sanev");
        testUser.setPassword("1234softunibg");
        testUser.setEnabled(true);
        testUser.setBirthDate(LocalDate.of(1997, 12, 15));
        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);
        testUser.setRoles(Set.of(userRole));

        when(mockUserRepository.findByUsername("SINGLE_ROLE_USER"))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = toTest.loadUserByUsername("SINGLE_ROLE_USER");

        assertInstanceOf(BassHeadsUserDetails.class, userDetails);

        assertEquals("SINGLE_ROLE_USER", userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());

        Set<String> expectedRoles = new HashSet<>(Set.of("ROLE_USER"));
        Set<String> actualRoles = new HashSet<>(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        assertEquals(expectedRoles, actualRoles);
    }
}