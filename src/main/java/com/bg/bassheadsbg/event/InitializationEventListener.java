package com.bg.bassheadsbg.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitializationEventListener {

    @EventListener
    public void handleInitializationEvent(InitializationEvent event) {
        log.info("Initialization Event: {}", event.getMessage());
    }
}