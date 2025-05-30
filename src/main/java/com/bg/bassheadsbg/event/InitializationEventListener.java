package com.bg.bassheadsbg.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitializationEventListener {

    private static final String TARGET = "{}";
    private static final String INITIALIZATION_EVENT = "Initialization Event: " + TARGET;

    @EventListener
    public void handleInitializationEvent(InitializationEvent event) {
        log.info(INITIALIZATION_EVENT, event.getMessage());
    }
}