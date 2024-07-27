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

    @ExceptionHandler(DeviceAlreadyExistsException.class)
    public String handleDeviceAlreadyExistsException(DeviceAlreadyExistsException ex) {
        log.error("DeviceAlreadyExistsException occurred: {}", ex.getMessage(), ex);
        return "error/already-exists";
    }

    @ExceptionHandler(JsonProcessingException.class)
    public String handleJsonProcessingException(JsonProcessingException ex) {
        log.error("JsonProcessingException occurred: {}", ex.getMessage(), ex);
        return "error/went-wrong";
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public String handleUserNotAuthenticatedException(UserNotAuthenticatedException ex, RedirectAttributes redirectAttributes) {
        log.error("UserNotAuthenticatedException occurred: {}", ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(DeviceAlreadyLikedException.class)
    public String handleDeviceAlreadyLikedException(DeviceAlreadyLikedException ex) {
        log.error("UserNotAuthenticatedException occurred: {}", ex.getMessage(), ex);
        return "error/already-liked";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException occurred: {}", ex.getMessage(), ex);
        return "error/not-found";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error("NoResourceFoundException occurred: {}", ex.getMessage(), ex);
        return "error/nothing-here";
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDeviceNotFoundException(DeviceNotFoundException ex) {
        log.error("DeviceNotFoundException occurred: {}", ex.getMessage(), ex);
        return "error/not-found";
    }
}