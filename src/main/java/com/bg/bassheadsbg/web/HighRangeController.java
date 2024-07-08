package com.bg.bassheadsbg.web;

import com.bg.bassheadsbg.model.dto.AddHighRangeDTO;
import com.bg.bassheadsbg.service.HighRangeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/speakers")
public class HighRangeController {
    private final HighRangeService highRangeService;

    public HighRangeController(HighRangeService highRangeService) {
        this.highRangeService = highRangeService;
    }

    @GetMapping("/add-highrange")
    public String addHighRange() {

        return "highrange-add";
    }

    @ModelAttribute("addHighRangeDTO")
    public AddHighRangeDTO addHighRangeDTO() {
        return new AddHighRangeDTO();
    }

    @PostMapping("/add-highrange")
    public String addHighRange(@Valid AddHighRangeDTO addHighRangeDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addHighRangeDTO", new AddHighRangeDTO());
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addHighRangeDTO"
                    , bindingResult);
            return "redirect:/speakers/add-highrange";
        }

        long newHighRange = highRangeService.addDevice(addHighRangeDTO);

        return "redirect:/speakers/" + newHighRange;
    }

    @GetMapping("/{id}")
    public String highRangeDetails(@PathVariable("id") Long id,
                                   Model model) {
        model.addAttribute("highRangeDetails", highRangeService.getDeviceDetails(id));

        return "highrange-details";
    }

    @DeleteMapping("/{id}")
    public String deleteHighRange(@PathVariable("id") Long id) {

        highRangeService.deleteDevice(id);

        return "redirect:/";
    }
}
