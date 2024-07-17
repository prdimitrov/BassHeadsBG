package com.bg.bassheadsbg.config.custom;

import com.bg.bassheadsbg.service.implementation.MessageStoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final MessageStoreService messageStoreService;

    public CustomLogoutHandler(MessageStoreService messageStoreService) {
        this.messageStoreService = messageStoreService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null && authentication.getName() != null) {
            messageStoreService.clearMessages(authentication.getName());
        }
    }
}