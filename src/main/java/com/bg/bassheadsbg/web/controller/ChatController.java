package com.bg.bassheadsbg.web.controller;

import com.bg.bassheadsbg.model.ChatMessage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.service.implementation.MessageStoreService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {

    private final UserService userService;
    private final MessageStoreService messageStoreService;

    public ChatController(UserService userService, MessageStoreService messageStoreService) {
        this.userService = userService;
        this.messageStoreService = messageStoreService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message, Principal principal) {
        Optional<UserEntity> userOptional = userService.findByUsername(principal.getName());

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Store message
            messageStoreService.storeMessage(user.getUsername(), message.getContent());

            // Modify message content based on user roles
            if (user.getRoles().stream().anyMatch(role -> role.getRole() == UserRoleEnum.ADMIN)) {
                message.setContent("(Admin)" + user.getUsername() + " said: " + message.getContent());
            } else {
                message.setContent(user.getUsername() + " said: " + message.getContent());
            }

            // Store original content in the message object
            message.setOriginalContent(message.getContent());
        } else {
            message.setContent("User not found: " + message.getContent());
        }

        return message;
    }

    @MessageMapping("/chat.loadMessages")
    @SendTo("/topic/public.init")
    public List<ChatMessage> loadMessages(Principal principal) {
        Optional<UserEntity> userOptional = userService.findByUsername(principal.getName());

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            List<ChatMessage> messages = messageStoreService.getMessages(user.getUsername());

            // Restore original content for each message
            messages.forEach(message -> message.setContent(message.getOriginalContent()));

            return messages;
        }

        return List.of(); // Return an empty list if the user is not found
    }
}