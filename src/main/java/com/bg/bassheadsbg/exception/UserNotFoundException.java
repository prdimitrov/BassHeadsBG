package com.bg.bassheadsbg.exception;

import com.bg.bassheadsbg.messages.ExceptionMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ExceptionMessages.USER_NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private String username;

    public UserNotFoundException(String message, String username) {
        super(message);
        this.username = username;
    }

    public Object getId() {
        return username;
    }
}
