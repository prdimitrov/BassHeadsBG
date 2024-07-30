package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.model.dto.details.BassHeadsUserDetails;
import com.bg.bassheadsbg.service.interfaces.WelcomeService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class WelcomeServiceImpl implements WelcomeService {
    public String generateWelcomeMessage(UserDetails userDetails) {
        if (userDetails instanceof BassHeadsUserDetails bassHeadsUserDetails) {
            return ", " + bassHeadsUserDetails.getUsername();
        } else {
            return " :)";
        }
    }
}