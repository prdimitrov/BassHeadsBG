package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MessageStoreService {

    private final ConcurrentHashMap<String, CopyOnWriteArrayList<ChatMessage>> userMessages = new ConcurrentHashMap<>();

    public void storeMessage(String username, String content) {
        ChatMessage message = new ChatMessage();
        message.setContent(content);
        userMessages.computeIfAbsent(username, k -> new CopyOnWriteArrayList<>()).add(message);
    }

    public List<ChatMessage> getMessages(String username) {
        return new ArrayList<>(userMessages.getOrDefault(username, new CopyOnWriteArrayList<>()));
    }

    public void clearMessages(String username) {
        userMessages.remove(username);
    }
}