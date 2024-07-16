package com.bg.bassheadsbg.exception;

import com.bg.bassheadsbg.messages.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ExceptionMessages.DEVICE_NOT_FOUND)
public class DeviceNotFoundException extends RuntimeException {

    private final Object id;

    public DeviceNotFoundException(String message, Object id) {
        super(message);
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}
