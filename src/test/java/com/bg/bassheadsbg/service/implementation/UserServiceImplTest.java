package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.UserNotFoundException;
import com.bg.bassheadsbg.model.dto.auth.UserRegistrationDTO;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.RoleService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("testUser");
        userRegistrationDTO.setPassword("testPassword");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setPassword("e1N1c1O1d1e1d1P1a1s1s1w1o1r1d");
        userEntity.setRoles(new HashSet<>());

        when(modelMapper.map(userRegistrationDTO, UserEntity.class)).thenReturn(userEntity);
        when(passwordEncoder.encode(userRegistrationDTO.getPassword())).thenReturn("e1N1c1O1d1e1d1P1a1s1s1w1o1r1d");

        userService.registerUser(userRegistrationDTO);

        verify(userRepository).save(userEntity);
    }

    @Test
    public void testFindAllUsers() {
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();
        List<UserEntity> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserEntity> result = userService.findAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    public void testFindByUsername_UserFound() {
        String username = "BojidarNotMissing";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userService.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());
    }

    @Test
    public void testFindByUsername_UserNotFound() {
        String username = "BojidarMissing";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<UserEntity> result = userService.findByUsername(username);

        assertFalse(result.isPresent());
    }

    @Test
    public void testAddRoleToUserId() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);
        Set<UserRole> roles = new HashSet<>();
        userEntity.setRoles(roles);

        when(roleService.findByName(UserRoleEnum.USER)).thenReturn(userRole);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        userService.addRoleToUserId("USER", userId);

        assertTrue(userEntity.getRoles().contains(userRole));
        verify(userRepository).save(userEntity);
    }

    @Test
    public void testRemoveRoleFromUserId() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();

        Set<UserRole> roles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);
        roles.add(userRole);

        userEntity.setRoles(roles);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(roleService.findByName(UserRoleEnum.USER)).thenReturn(userRole);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        userService.removeRoleToUserId("USER", userId);

        assertFalse(userEntity.getRoles().contains(userRole));
        verify(userRepository).save(userEntity);
    }

    @Test
    public void testEnableUser_UserFound() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setEnabled(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserEntity result = userService.enableUser(userId);

        assertNotNull(result);
        assertTrue(result.isEnabled());
        verify(userRepository).save(result);
    }

    @Test
    public void testEnableUser_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.enableUser(userId);
        });

        assertEquals(userId, thrown.getUserId());
    }

    @Test
    public void testDisableUser_UserFound() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setEnabled(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserEntity result = userService.disableUser(userId);

        assertNotNull(result);
        assertFalse(result.isEnabled());
        verify(userRepository).save(result);
    }

    @Test
    public void testDisableUser_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.disableUser(userId);
        });

        assertEquals(userId, thrown.getUserId());
    }

    @Test
    public void testIsAccountDisabled_UserFoundAndDisabled() {
        String username = "oneMen";
        UserEntity userEntity = new UserEntity();
        userEntity.setEnabled(false);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        boolean result = userService.isAccountDisabled(username);

        assertTrue(result);
    }

    @Test
    public void testIsAccountDisabled_UserFoundAndEnabled() {
        String username = "oneMen";
        UserEntity userEntity = new UserEntity();
        userEntity.setEnabled(true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        boolean result = userService.isAccountDisabled(username);

        assertFalse(result);
    }

    @Test
    public void testIsAccountDisabled_UserNotFound() {
        String username = "noMen";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.isAccountDisabled(username);
        });

        assertEquals(username, thrown.getUsername());
    }
}
