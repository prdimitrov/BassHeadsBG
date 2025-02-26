package com.bg.bassheadsbg.web.controller.amplifierControllers;

import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.service.interfaces.MultiChannelAmpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/amplifiers/multi-channel-amplifiers")
public class MultiChannelAmplifierController {
    private final MultiChannelAmpService multiChannelAmpService;

    public MultiChannelAmplifierController(MultiChannelAmpService multiChannelAmpService) {
        this.multiChannelAmpService = multiChannelAmpService;
    }

    @GetMapping("/add")
    public String addMultiChannelAmp(Model model) {
        if (!model.containsAttribute("addMultiChannelAmpDTO")) {
            model.addAttribute("addMultiChannelAmpDTO", multiChannelAmpService.createNewAmplifier());
        }
        return "amplifiers/multichannel-amp-add";
    }

    @PostMapping("/add")
    public String addMultiChannelAmp(@Valid @ModelAttribute("addMultiChannelAmpDTO") AddMultiChannelAmpDTO addMultiChannelAmpDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMultiChannelAmpDTO", addMultiChannelAmpDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMultiChannelAmpDTO", bindingResult);
            return "redirect:/amplifiers/multi-channel-amplifiers/add";
        }
        return "redirect:/amplifiers/multi-channel-amplifiers/" + multiChannelAmpService.addAmplifier(addMultiChannelAmpDTO);
    }

    @GetMapping("/edit/{id}")
    public String getEditMultiChannelAmp(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("multiChannelAmpDetails")) {
            model.addAttribute("multiChannelAmpDetails", multiChannelAmpService.getAmplifierDetails(id));
        }
        return "amplifiers/multichannel-amp-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditMultiChannelAmp(@Valid @ModelAttribute("multiChannelAmpDetails") AddMultiChannelAmpDTO addMultiChannelAmpDTO,
                                          BindingResult bindingResult,
                                          @RequestParam(required = false) List<MultipartFile> multipartFiles,
                                          RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("multiChannelAmpDetails", addMultiChannelAmpDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "multiChannelAmpDetails", bindingResult);
            return "redirect:/amplifiers/multi-channel-amplifiers/edit/" + addMultiChannelAmpDTO.getId();
        }
        return "redirect:/amplifiers/multi-channel-amplifiers/" + multiChannelAmpService.editAmplifier(addMultiChannelAmpDTO, multipartFiles);
    }

    @GetMapping("/{id}")
    public String multiChannelAmpDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("multiChannelAmpDetails", multiChannelAmpService.getAmplifierDetails(id));
        model.addAttribute("helperDTO", multiChannelAmpService.getAmplifierDetailsHelper(id));
        return "amplifiers/multichannel-amp-details";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMultiChannelAmp(@PathVariable("id") Long id) {
        multiChannelAmpService.deleteAmplifier(id);
        return "redirect:/";
    }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("allDevices", multiChannelAmpService.getAllAmplifierSummary());
        return "amplifiers/multichannel-amp-all";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable("id") Long id) {
        multiChannelAmpService.likeAmplifier(id);
        return "redirect:/amplifiers/multi-channel-amplifiers/rankings";
    }
}