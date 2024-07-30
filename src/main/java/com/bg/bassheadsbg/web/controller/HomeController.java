package com.bg.bassheadsbg.web.controller;

import com.bg.bassheadsbg.service.interfaces.WelcomeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final WelcomeService welcomeService;

    public HomeController(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails,
                       Model model) {
        model.addAttribute("welcomeMessage", welcomeService.generateWelcomeMessage(userDetails));
        return "index";
    }
}