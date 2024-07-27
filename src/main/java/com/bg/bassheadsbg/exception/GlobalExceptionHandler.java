package com.bg.bassheadsbg.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeviceAlreadyExistsException.class)
    public String handleDeviceAlreadyExistsException(DeviceAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
        log.error("DeviceAlreadyExistsException occurred: {}", ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/speakers/high-range/add";
    }

    @ExceptionHandler(JsonProcessingException.class)
    public String handleJsonProcessingException(JsonProcessingException ex, RedirectAttributes redirectAttributes) {
        log.error("JsonProcessingException occurred: {}", ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request.");
        return "redirect:/speakers/high-range/add";
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public String handleUserNotAuthenticatedException(UserNotAuthenticatedException ex, RedirectAttributes redirectAttributes) {
        log.error("UserNotAuthenticatedException occurred: {}", ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(DeviceAlreadyLikedException.class)
    public String handleDeviceAlreadyLikedException(DeviceAlreadyLikedException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/speakers/high-range/rankings";
    }
}