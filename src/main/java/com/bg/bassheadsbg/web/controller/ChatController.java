package com.bg.bassheadsbg.web.controller;

import com.bg.bassheadsbg.model.entity.users.ChatMessage;
import com.bg.bassheadsbg.service.implementation.MessageStoreService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    private final MessageStoreService messageStoreService;

    public ChatController(MessageStoreService messageStoreService) {
        this.messageStoreService = messageStoreService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message, Principal principal) {
        return messageStoreService.storeMessage(principal.getName(), message.getContent());
    }

    @MessageMapping("/chat.loadMessages")
    @SendTo("/topic/public.init")
    public List<ChatMessage> loadMessages(Principal principal) {
        return messageStoreService.getMessages(principal.getName())
                .stream()
                .peek(message -> message.setContent(message.getOriginalContent()))
                .toList();
    }
}