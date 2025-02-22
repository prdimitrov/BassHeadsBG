package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.entity.users.ChatMessage;

import java.util.List;

public interface MessageStoreService {
    ChatMessage storeMessage(String username, String content);

    List<ChatMessage> getMessages(String username);

    void clearMessages(String username);
}
