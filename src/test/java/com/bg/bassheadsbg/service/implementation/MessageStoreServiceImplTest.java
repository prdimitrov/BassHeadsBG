package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.entity.users.ChatMessage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.entity.users.UserRole;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MessageStoreServiceImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private MessageStoreServiceImpl messageStoreServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UserRole createUserRole(UserRoleEnum roleEnum) {
        UserRole role = new UserRole();
        role.setRole(roleEnum);
        return role;
    }

    @Test
    public void testStoreMessage_UserNotFound() {
        String username = "nonexistentUser";
        String content = "Hello, world!";

        when(userService.findByUsername(username)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            messageStoreServiceImpl.storeMessage(username, content);
        });

        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    public void testStoreMessage_Admin() {
        String username = "adminUser";
        String content = "Hello, admin!";
        UserRole adminRole = createUserRole(UserRoleEnum.ADMIN);
        UserEntity adminUser = new UserEntity();
        adminUser.setUsername(username);
        adminUser.setRoles(Set.of(adminRole));

        when(userService.findByUsername(username)).thenReturn(Optional.of(adminUser));

        ChatMessage message = messageStoreServiceImpl.storeMessage(username, content);

        assertNotNull(message);
        assertTrue(message.getContent().contains("(Admin)"));
        assertTrue(message.getContent().contains(username));
        assertTrue(message.getContent().contains(content));
    }

    @Test
    public void testStoreMessage_NormalUser() {
        String username = "normalUser";
        String content = "Hello, user!";

        UserRole userRole = createUserRole(UserRoleEnum.USER);
        UserEntity normalUser = new UserEntity();
        normalUser.setUsername(username);
        normalUser.setRoles(Set.of(userRole));

        when(userService.findByUsername(username)).thenReturn(Optional.of(normalUser));

        ChatMessage message = messageStoreServiceImpl.storeMessage(username, content);

        assertNotNull(message);
        assertFalse(message.getContent().contains("(Admin)"));
        assertTrue(message.getContent().contains(username));
        assertTrue(message.getContent().contains(content));
    }

    @Test
    public void testGetMessages_Empty() {
        String username = "newUser";

        List<ChatMessage> messages = messageStoreServiceImpl.getMessages(username);

        assertNotNull(messages);
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testGetMessages_WithMessages() {
        String username = "userWithMessages";
        String content = "Test message";

        UserRole userRole = createUserRole(UserRoleEnum.USER);
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setRoles(Set.of(userRole));

        when(userService.findByUsername(username)).thenReturn(Optional.of(user));

        messageStoreServiceImpl.storeMessage(username, content);

        List<ChatMessage> messages = messageStoreServiceImpl.getMessages(username);

        assertNotNull(messages);
        assertEquals(1, messages.size());
    }

    @Test
    public void testClearMessages() {
        String username = "userToClear";
        UserRole userRole = createUserRole(UserRoleEnum.USER);
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setRoles(Set.of(userRole));

        when(userService.findByUsername(username)).thenReturn(Optional.of(user));

        messageStoreServiceImpl.storeMessage(username, "Message to be cleared");

        assertEquals(1, messageStoreServiceImpl.getMessages(username).size());

        messageStoreServiceImpl.clearMessages(username);

        assertTrue(messageStoreServiceImpl.getMessages(username).isEmpty());
    }

    @Test
    public void testStoreMessage_WithMultipleMessages() {
        String username = "userMultipleMessages";
        UserRole userRole = createUserRole(UserRoleEnum.USER);
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setRoles(Set.of(userRole));

        when(userService.findByUsername(username)).thenReturn(Optional.of(user));

        messageStoreServiceImpl.storeMessage(username, "First message");
        messageStoreServiceImpl.storeMessage(username, "Second message");

        List<ChatMessage> messages = messageStoreServiceImpl.getMessages(username);

        assertNotNull(messages);
        assertEquals(2, messages.size());
        assertTrue(messages.get(0).getContent().contains("First message"));
        assertTrue(messages.get(1).getContent().contains("Second message"));
    }
}