package com.bg.bassheadsbg.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeviceNotFoundException.class)
    public ModelAndView handleDeviceNotFoundException(DeviceNotFoundException ex) {
        log.error("DeviceNotFoundException occurred: {}", ex.getMessage(), ex);

        ModelAndView modelAndView = new ModelAndView("/error/not-found");
        modelAndView.addObject("name", ex.getId());
        return modelAndView;
    }
}