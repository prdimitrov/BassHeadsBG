package com.bg.bassheadsbg.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeviceAlreadyLikedException.class)
    public ModelAndView handleDeviceAlreadyLikedException(DeviceAlreadyLikedException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("error/already-liked");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }
}
