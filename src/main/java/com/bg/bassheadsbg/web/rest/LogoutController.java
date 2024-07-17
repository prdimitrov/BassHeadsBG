package com.bg.bassheadsbg.web.rest;

import com.bg.bassheadsbg.service.implementation.MessageStoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LogoutController {

    private final MessageStoreService messageStoreService;

    public LogoutController(MessageStoreService messageStoreService) {
        this.messageStoreService = messageStoreService;
    }

    @PostMapping("/logout")
    public void logout(Principal principal) {
        if (principal != null) {
            messageStoreService.clearMessages(principal.getName());
        }
    }
}