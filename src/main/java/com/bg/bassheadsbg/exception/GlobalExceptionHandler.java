package com.bg.bassheadsbg.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String TARGET = "{}";
    private static final String DEVICE_NOT_FOUND_EXCEPTION = "DeviceNotFoundException occurred: " + TARGET;
    private static final String NO_RESOURCE_FOUND_EXCEPTION = "NoResourceFoundException occurred: " + TARGET;
    private static final String NO_HANDLER_FOUND_EXCEPTION = "NoHandlerFoundException occurred: " + TARGET;
    private static final String USER_NOT_AUTH_EXCEPTION = "UserNotAuthenticatedException occurred: " + TARGET;
    private static final String JSON_PROCESSING_EXCEPTION = "JsonProcessingException occurred: " + TARGET;
    private static final String DEVICE_ALREADY_EXISTS_EXCEPTION = "DeviceAlreadyExistsException occurred: " + TARGET;
    private static final String ERROR = "error";
    private static final String ERROR_NOTHING_THERE = ERROR + "/nothing-here";
    private static final String ERROR_NOT_FOUND = ERROR + "/not-found";
    private static final String ERROR_ALREADY_LIKED = ERROR + "/already-liked";
    private static final String ERROR_MESSAGE = ERROR + "Message";
    private static final String WENT_WRONG = ERROR + "/went-wrong";
    private static final String ALREADY_EXISTS = ERROR + "/already-exists";
    private static final String REDIRECT_LOGIN = "redirect:/login";

    @ExceptionHandler(DeviceAlreadyExistsException.class)
    public String handleDeviceAlreadyExistsException(DeviceAlreadyExistsException ex) {
        log.error(DEVICE_ALREADY_EXISTS_EXCEPTION, ex.getMessage(), ex);
        return ALREADY_EXISTS;
    }

    @ExceptionHandler(JsonProcessingException.class)
    public String handleJsonProcessingException(JsonProcessingException ex) {
        log.error(JSON_PROCESSING_EXCEPTION, ex.getMessage(), ex);
        return WENT_WRONG;
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public String handleUserNotAuthenticatedException(UserNotAuthenticatedException ex, RedirectAttributes redirectAttributes) {
        log.error(USER_NOT_AUTH_EXCEPTION, ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, ex.getMessage());
        return REDIRECT_LOGIN;
    }

    @ExceptionHandler(DeviceAlreadyLikedException.class)
    public String handleDeviceAlreadyLikedException(DeviceAlreadyLikedException ex) {
        log.error(USER_NOT_AUTH_EXCEPTION, ex.getMessage(), ex);
        return ERROR_ALREADY_LIKED;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error(NO_HANDLER_FOUND_EXCEPTION, ex.getMessage(), ex);
        return ERROR_NOT_FOUND;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error(NO_RESOURCE_FOUND_EXCEPTION, ex.getMessage(), ex);
        return ERROR_NOTHING_THERE;
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDeviceNotFoundException(DeviceNotFoundException ex) {
        log.error(DEVICE_NOT_FOUND_EXCEPTION, ex.getMessage(), ex);
        return ERROR_NOT_FOUND;
    }
}