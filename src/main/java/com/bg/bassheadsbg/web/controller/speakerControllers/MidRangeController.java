package com.bg.bassheadsbg.web.controller.speakerControllers;

import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.service.interfaces.MidRangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/speakers/mid-range")
public class MidRangeController {
    private final MidRangeService midRangeService;

    public MidRangeController(MidRangeService midRangeService) {
        this.midRangeService = midRangeService;
    }

    @GetMapping("/add")
    public String addMidRange(Model model) {
        if (!model.containsAttribute("addMidRangeDTO")) {
            model.addAttribute("addMidRangeDTO", midRangeService.createNewAddMidRangeDTO());
        }
        return "speakers/midrange-add";
    }

    @PostMapping("/add")
    public String addMidRange(@Valid @ModelAttribute("addMidRangeDTO") AddMidRangeDTO addMidRangeDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMidRangeDTO", addMidRangeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMidRangeDTO", bindingResult);
            return "redirect:/speakers/mid-range/add";
        }
        return "redirect:/speakers/mid-range/" + midRangeService.addDevice(addMidRangeDTO);
    }

    @GetMapping("/edit/{id}")
    public String getEditMidRange(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("midRangeDetails")) {
            model.addAttribute("midRangeDetails", midRangeService.getDeviceDetails(id));
        }
        return "speakers/midrange-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditMidRange(@Valid @ModelAttribute("midRangeDetails") AddMidRangeDTO addMidRangeDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("midRangeDetails", addMidRangeDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "midRangeDetails", bindingResult);
            return "redirect:/speakers/mid-range/edit/" + addMidRangeDTO.getId();
        }
        return "redirect:/speakers/mid-range/" + midRangeService.editDevice(addMidRangeDTO);
    }

    @GetMapping("/{id}")
    public String midRangeDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("midRangeDetails", midRangeService.getDeviceDetails(id));
        model.addAttribute("helperDTO", midRangeService.getDeviceDetailsHelper(id));
        return "speakers/midrange-details";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMidRange(@PathVariable("id") Long id) {
        midRangeService.deleteDevice(id);
        return "redirect:/";
    }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("allDevices", midRangeService.getAllDeviceSummary());
        return "speakers/midrange-all";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable("id") Long id) {
        midRangeService.likeDevice(id);
        return "redirect:/speakers/mid-range/rankings";
    }
}