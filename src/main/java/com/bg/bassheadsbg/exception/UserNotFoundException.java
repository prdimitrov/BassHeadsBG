package com.bg.bassheadsbg.exception;

import com.bg.bassheadsbg.messages.ExceptionMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ExceptionMessages.USER_NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        super(String.format("%s ID: %d", ExceptionMessages.USER_NOT_FOUND, userId));
        this.userId = userId;
    }

}
