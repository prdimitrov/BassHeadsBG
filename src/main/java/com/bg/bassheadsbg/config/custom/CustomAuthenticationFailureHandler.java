package com.bg.bassheadsbg.config.custom;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String USER_IS_DISABLED = "User is disabled";
    private static final String USERS_LOGIN_ERROR_DISABLED = "/users/login-error?error=disabled";
    private static final String USERS_LOGIN_ERROR_TRUE = "/users/login-error?error=true";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (exception.getMessage().equalsIgnoreCase(USER_IS_DISABLED)) {
            response.sendRedirect(USERS_LOGIN_ERROR_DISABLED);
        } else {
            response.sendRedirect(USERS_LOGIN_ERROR_TRUE)
            ;
        }
    }
}