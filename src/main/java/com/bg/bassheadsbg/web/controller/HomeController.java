package com.bg.bassheadsbg.web.controller;

import com.bg.bassheadsbg.config.ImageProducer;
import com.bg.bassheadsbg.model.details.BassHeadsUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ImageProducer imageProducer;

    public HomeController(ImageProducer imageProducer) {
        this.imageProducer = imageProducer;
    }


    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails,
                       Model model) {
        imageProducer.sendMessage("test sendMessage");
        imageProducer.sendMessage2("test2 SendMessage2");
        if (userDetails instanceof BassHeadsUserDetails bassHeadsUserDetails) {
            model.addAttribute("welcomeMessage", bassHeadsUserDetails.getUsername());
        } else {
            model.addAttribute("welcomeMessage", "Anonymous");
        }

        return "index";
    }
}
