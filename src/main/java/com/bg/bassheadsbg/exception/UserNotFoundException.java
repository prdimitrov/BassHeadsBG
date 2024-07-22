package com.bg.bassheadsbg.exception;

import com.bg.bassheadsbg.messages.ExceptionMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ExceptionMessages.USER_NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final Long userId;
    private final String username;

    // Constructor for ID
    public UserNotFoundException(Long userId) {
        super(String.format("%s ID: %d", ExceptionMessages.USER_NOT_FOUND, userId));
        this.userId = userId;
        this.username = null;
    }

    // Constructor for username
    public UserNotFoundException(String username) {
        super(String.format("%s Username: %s", ExceptionMessages.USER_NOT_FOUND, username));
        this.userId = null;
        this.username = username;
    }
}