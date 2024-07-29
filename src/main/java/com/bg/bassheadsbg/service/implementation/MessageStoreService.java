package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.entity.users.ChatMessage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Service, thatis used for managing chat messages, storing, retrieving, and clearing messages for users.
 * This class uses in-memory storage with thread-safe collections.
 */
@Service
public class MessageStoreService {

    private final ConcurrentHashMap<String, CopyOnWriteArrayList<ChatMessage>> userMessages = new ConcurrentHashMap<>();
    private final UserService userService;
    private final DateTimeFormatter dateTimeFormatter;

    public MessageStoreService(UserService userService) {
        this.userService = userService;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * This method stores a new chat message for a user.
     *
     * @param username the username of the sender
     * @param content  the content of the message
     * @return the stored ChatMessage
     * @throws IllegalArgumentException if the user with the given username is not found
     */
    public ChatMessage storeMessage(String username, String content) {
        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ChatMessage message = new ChatMessage();
        message.setContent(content);

        String formattedDateTime = LocalDateTime.now().format(dateTimeFormatter);

        if (user.getRoles().stream().anyMatch(role -> role.getRole() == UserRoleEnum.ADMIN)) {
            message.setContent(formattedDateTime + " (Admin) " + user.getUsername() + ": " + content);
        } else {
            message.setContent(formattedDateTime + " " + user.getUsername() + ": " + content);
        }

        message.setOriginalContent(message.getContent());

        userMessages.computeIfAbsent(username, k -> new CopyOnWriteArrayList<>()).add(message);
        return message;
    }

    /**
     * The method retrieves all chat messages for a user.
     *
     * @param username the username of the user whose messages should be retrieved
     * @return a list of ChatMessage for the specified user
     */
    public List<ChatMessage> getMessages(String username) {
        return new ArrayList<>(userMessages.getOrDefault(username, new CopyOnWriteArrayList<>()));
    }

    /**
     * The method clears all chat messages for a user.
     *
     * @param username the username of the user whose messages should be cleared
     */
    public void clearMessages(String username) {
        userMessages.remove(username);
    }
}