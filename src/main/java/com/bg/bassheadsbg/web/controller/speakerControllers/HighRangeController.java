package com.bg.bassheadsbg.web.controller.speakerControllers;

import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.service.interfaces.HighRangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/speakers/high-range")
public class HighRangeController {
    private final HighRangeService highRangeService;

    public HighRangeController(HighRangeService highRangeService) {
        this.highRangeService = highRangeService;
    }

    @GetMapping("/add")
    public String addHighRange(Model model) {
        if (!model.containsAttribute("addHighRangeDTO")) {
            model.addAttribute("addHighRangeDTO", highRangeService.createNewAddHighRangeDTO());
        }
        return "speakers/highrange-add";
    }

    @PostMapping("/add")
    public String addHighRange(@Valid @ModelAttribute("addHighRangeDTO") AddHighRangeDTO addHighRangeDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addHighRangeDTO", addHighRangeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addHighRangeDTO", bindingResult);
            return "redirect:/speakers/high-range/add";
        }
        return "redirect:/speakers/high-range/" + highRangeService.addDevice(addHighRangeDTO);
    }

    @GetMapping("/edit/{id}")
    public String getEditHighRange(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("highRangeDetails")) {
            model.addAttribute("highRangeDetails", highRangeService.getDeviceDetails(id));
        }
        return "speakers/highrange-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditHighRange(@Valid @ModelAttribute("highRangeDetails") AddHighRangeDTO addHighRangeDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("highRangeDetails", addHighRangeDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "highRangeDetails", bindingResult);
            return "redirect:/speakers/high-range/edit/" + addHighRangeDTO.getId();
        }
        return "redirect:/speakers/high-range/" + highRangeService.editDevice(addHighRangeDTO);
    }

    @GetMapping("/{id}")
    public String highRangeDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("highRangeDetails", highRangeService.getDeviceDetails(id));
        model.addAttribute("helperDTO", highRangeService.getDeviceDetailsHelper(id));
        return "speakers/highrange-details";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteHighRange(@PathVariable("id") Long id) {
        highRangeService.deleteDevice(id);
        return "redirect:/";
    }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("allDevices", highRangeService.getAllDeviceSummary());
        return "speakers/highrange-all";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable("id") Long id) {
        highRangeService.likeDevice(id);
        return "redirect:/speakers/high-range/rankings";
    }
}