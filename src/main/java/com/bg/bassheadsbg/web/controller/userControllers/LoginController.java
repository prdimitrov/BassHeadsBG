package com.bg.bassheadsbg.web.controller.userControllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class LoginController {

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String error = request.getParameter("error");
        if ("disabled".equals(error)) {
            model.addAttribute("accountDisabled", true);
        } else if ("true".equals(error)) {
            model.addAttribute("wrongCredentials", true);
        }
        return "users/auth-login"; // No leading slash
    }

    @GetMapping("/login-error")
    public String loginError(HttpServletRequest request, Model model) {
        String error = request.getParameter("error");
        if ("disabled".equals(error)) {
            model.addAttribute("accountDisabled", true);
        } else if ("true".equals(error)) {
            model.addAttribute("wrongCredentials", true);
        }
        return "users/auth-login"; // No leading slash
    }
}