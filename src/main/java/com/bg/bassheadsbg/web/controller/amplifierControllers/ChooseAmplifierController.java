package com.bg.bassheadsbg.web.controller.amplifierControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/amplifiers")
public class ChooseAmplifierController {
    @GetMapping("/choose-amplifier")
    public String chooseAmp() {
        return "/amplifiers/choose-amplifier";
    }
}
