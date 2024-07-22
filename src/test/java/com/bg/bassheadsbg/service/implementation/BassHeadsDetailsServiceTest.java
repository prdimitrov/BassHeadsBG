package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.details.BassHeadsUserDetails;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

public class BassHeadsDetailsServiceTest {

    static final String TEST_EMAIL = "ancho@abv.bg";

    static final String NOT_EXISTENT_EMAIL = "noone@example.com";

    static final String TEST_USERNAME = "DASKAL";

    private BassHeadsDetailsService toTest;

    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        mockUserRepository = Mockito.mock(UserRepository.class);

        toTest = new BassHeadsDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {

        // Arrange
        UserEntity testUser = new UserEntity();
        testUser.setEmail(TEST_EMAIL);
        testUser.setUsername(TEST_USERNAME);
        testUser.setFirstName("Sani");
        testUser.setLastName("Sanev");
        testUser.setPassword("1234softunibg");
        testUser.setEnabled(true);
        testUser.setBirthDate(LocalDate.ofEpochDay(1997-15-12));
        UserRole adminRole = new UserRole();
        adminRole.setRole(UserRoleEnum.USER);
        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.ADMIN);
        testUser.setRoles(Set.of(userRole, adminRole));

        /*Mockito.*/when(mockUserRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testUser));

        // Act
        UserDetails userDetails = toTest.loadUserByUsername(TEST_USERNAME);

        // Assert
        Assertions.assertInstanceOf(BassHeadsUserDetails.class, userDetails);

        BassHeadsUserDetails bassHeadsUserDetails = (BassHeadsUserDetails) userDetails;

        Assertions.assertEquals(TEST_USERNAME, userDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());

        var exectedRoles = testUser.getRoles().stream().map(UserRole::getRole).map(r -> "ROLE_" + r).toList();
        var actualRoles = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).toList();

        Assertions.assertEquals(exectedRoles, actualRoles);
    }
}
