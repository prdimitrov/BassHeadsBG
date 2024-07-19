package com.bg.bassheadsbg.web.controller;

import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.model.dto.details.BassHeadsUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    public HomeController() {
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails,
                       Model model) {
        //FIXME: This was used to test the kafka!
        //        ImageListDTO temp = new ImageListDTO();
        //        temp.imageUrls = new ArrayList<String>();
        //        temp.imageUrls.add("http://caraudio-bg.com/img/products/products_1869_444333028400.4%20EVO6--.jpg");
        //        imageProducer.sendMessage(temp);
        if (userDetails instanceof BassHeadsUserDetails bassHeadsUserDetails) {
            model.addAttribute("welcomeMessage", (", " + bassHeadsUserDetails.getUsername()));
        } else {
            model.addAttribute("welcomeMessage", " :)");
        }

        return "index";
    }
}
