package com.bg.bassheadsbg.event;

import com.bg.bassheadsbg.model.entity.users.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final UserEntity user;
    private final Locale locale;
    private final String appUrl;
    private final String token;


    public OnRegistrationCompleteEvent(UserEntity user, Locale locale, String appUrl, String token) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.token = token;
    }
}
