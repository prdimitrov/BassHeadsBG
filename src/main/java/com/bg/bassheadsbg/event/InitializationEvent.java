package com.bg.bassheadsbg.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InitializationEvent extends ApplicationEvent {

    private final String message;

    public InitializationEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}