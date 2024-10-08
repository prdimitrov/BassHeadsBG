package com.bg.bassheadsbg.model.dto.details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class BassHeadsUserDetails extends User {

    @Getter
    private final Long id;
    private final String username;
    @Getter
    private final boolean enabled;

    public BassHeadsUserDetails(
            Long id,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled) {
        super(username, password, enabled, true, true, true, authorities);
        this.id = id;
        this.username = username;
        this.enabled = enabled;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
