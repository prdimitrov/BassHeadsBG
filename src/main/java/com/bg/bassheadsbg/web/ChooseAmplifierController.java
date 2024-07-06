package com.bg.bassheadsbg.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/amplifier")
public class ChooseAmplifierController {
    @GetMapping("/choose-amplifier")
    public String chooseAmp() {
        return "choose-amplifier";
    }
}
