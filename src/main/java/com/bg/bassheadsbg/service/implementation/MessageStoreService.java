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

@Service
public class MessageStoreService {

    private final ConcurrentHashMap<String, CopyOnWriteArrayList<ChatMessage>> userMessages = new ConcurrentHashMap<>();
    private final UserService userService;
    private final DateTimeFormatter dateTimeFormatter;

    public MessageStoreService(UserService userService) {
        this.userService = userService;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

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

    public List<ChatMessage> getMessages(String username) {
        return new ArrayList<>(userMessages.getOrDefault(username, new CopyOnWriteArrayList<>()));
    }

    public void clearMessages(String username) {
        userMessages.remove(username);
    }
}