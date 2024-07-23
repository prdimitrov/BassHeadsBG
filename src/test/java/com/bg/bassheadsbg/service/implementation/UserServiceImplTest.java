package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.auth.UserRegistrationDTO;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Disabled
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl toTest;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleService mockRoleService;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void setUp() {
        toTest = new UserServiceImpl(
                new ModelMapper(),
                mockPasswordEncoder,
                mockUserRepository,
                mockRoleService
        );
    }

    @Test
    void testUserRegistration() {
        // Arrange
        UserRegistrationDTO userRegistrationDTO =
                new UserRegistrationDTO();
        userRegistrationDTO.setFirstName("Pesho");
        userRegistrationDTO.setLastName("Pesho");
        userRegistrationDTO.setEmail("edingospodin@abv.bg");
        userRegistrationDTO.setPassword("topsecret");
        userRegistrationDTO.setBirthDate(LocalDate.ofEpochDay(2005-15-12));

        // Act
        toTest.registerUser(userRegistrationDTO);

        //TODO:
    }
}
