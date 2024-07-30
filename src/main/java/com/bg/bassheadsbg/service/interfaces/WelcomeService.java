package com.bg.bassheadsbg.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface WelcomeService {
    String generateWelcomeMessage(UserDetails userDetails);
}
