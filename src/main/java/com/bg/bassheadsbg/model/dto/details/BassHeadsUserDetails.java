package com.bg.bassheadsbg.model.dto.details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class BassHeadsUserDetails extends User {

    private final String username;
    @Getter
    private final boolean enabled;

    public BassHeadsUserDetails(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled) {
        super(username, password, enabled, true, true, true, authorities);
        this.username = username;
        this.enabled = enabled;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
