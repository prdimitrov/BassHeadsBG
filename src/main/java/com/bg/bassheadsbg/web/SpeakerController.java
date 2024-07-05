package com.bg.bassheadsbg.web;

import com.bg.bassheadsbg.model.dto.AddSubwooferDTO;
import com.bg.bassheadsbg.service.SubwooferService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/speakers")
public class SpeakerController {
    private final SubwooferService subwooferService;

    public SpeakerController(SubwooferService subwooferService) {
        this.subwooferService = subwooferService;
    }

    @GetMapping("/add-subwoofer")
    public String addSubwoofer() {

        return "subwoofer-add";
    }

    @ModelAttribute("addSubwooferDTO")
    public AddSubwooferDTO addSubwooferDTO() {
        return new AddSubwooferDTO();
    }

    @PostMapping("/add-subwoofer")
    public String addSubwoofer(@Valid AddSubwooferDTO addSubwooferDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addSubwooferDTO", new AddSubwooferDTO());
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addSubwooferDTO"
                    , bindingResult);
            return "redirect:/speakers/add-subwoofer";
        }

        subwooferService.addSubwoofer(addSubwooferDTO);
        return "redirect:/home";
    }
}
