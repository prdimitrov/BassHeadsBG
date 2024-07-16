package com.bg.bassheadsbg.web.controller;

import com.bg.bassheadsbg.model.dto.ChatMessage;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.enums.UserRoleEnum;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ChatController {

    private final UserService userService;

    public ChatController(UserService userService) {
        this.userService = userService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message, Principal principal) {
        Optional<UserEntity> userOptional = userService.findByUsername(principal.getName());

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // Check roles using UserRoleEnum
            boolean isAdmin = user.getRoles().stream()
                    .anyMatch(role -> role.getRole() == UserRoleEnum.ADMIN);

            boolean isUser = user.getRoles().stream()
                    .anyMatch(role -> role.getRole() == UserRoleEnum.USER);

            if (isAdmin) {
                // Admin specific logic
                message.setContent("(Admin)" + user.getUsername() + " said: " + message.getContent());
            } else if (isUser) {
                // User specific logic
                message.setContent(user.getUsername() + " said: " + message.getContent());
            } else {
                // Handle case where user has no roles (optional based on your application logic)
                message.setContent("Unidentified said: " + message.getContent());
            }
        } else {
            // Handle case where user is not found (optional based on your application logic)
            message.setContent("User not found: " + message.getContent());
        }

        return message;
    }
}