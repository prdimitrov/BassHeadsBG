package com.bg.bassheadsbg.web;

import com.bg.bassheadsbg.model.dto.AddMidRangeDTO;
import com.bg.bassheadsbg.service.MidRangeService;
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
public class MidRangeController {
    private final MidRangeService midRangeService;

    public MidRangeController(MidRangeService midRangeService) {
        this.midRangeService = midRangeService;
    }

    @GetMapping("/add-midrange")
    public String addMidRange() {

        return "midrange-add";
    }

    @ModelAttribute("addMidRangeDTO")
    public AddMidRangeDTO addMidRangeDTO() {
        return new AddMidRangeDTO();
    }

    @PostMapping("/add-midrange")
    public String addMidRange(@Valid AddMidRangeDTO addMidRangeDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMidRangeDTO", new AddMidRangeDTO());
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addMidRangeDTO"
                    , bindingResult);
            return "redirect:/speakers/midrange-add";
        }

        midRangeService.addMidRange(addMidRangeDTO);
        return "redirect:/home";
    }
}
