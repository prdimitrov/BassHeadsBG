package com.bg.bassheadsbg.model.dto.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class BassHeadsUserDetails extends User {

    private final String username;

    public BassHeadsUserDetails(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
