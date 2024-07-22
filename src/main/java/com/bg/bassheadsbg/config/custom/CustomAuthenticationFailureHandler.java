package com.bg.bassheadsbg.config.custom;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            response.sendRedirect("/users/login-error?error=disabled");
        } else {
            response.sendRedirect("/users/login-error?error=true");
        }
    }
}