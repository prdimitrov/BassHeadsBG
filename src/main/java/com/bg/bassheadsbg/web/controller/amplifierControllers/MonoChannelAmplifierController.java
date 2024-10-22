package com.bg.bassheadsbg.web.controller.amplifierControllers;


import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/amplifiers/mono-amplifiers")
public class MonoChannelAmplifierController {
    private final MonoAmpService monoAmpService;

    public MonoChannelAmplifierController(MonoAmpService monoAmpService) {
        this.monoAmpService = monoAmpService;
    }

    @GetMapping("/add")
    public String addMonoAmp(Model model) {
        if (!model.containsAttribute("addMonoAmpDTO")) {
            model.addAttribute("addMonoAmpDTO", monoAmpService.createNewAddMonoAmpDTO());
        }
        return "amplifiers/monoamp-add";
    }

    @PostMapping("/add")
    public String addMonoAmp(@Valid @ModelAttribute("addMonoAmpDTO") AddMonoAmpDTO addMonoAmpDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMonoAmpDTO", addMonoAmpDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMonoAmpDTO", bindingResult);
            return "redirect:/amplifiers/mono-amplifiers/add";
        }
        return "redirect:/amplifiers/mono-amplifiers/" + monoAmpService.addDevice(addMonoAmpDTO);
    }

    @GetMapping("/edit/{id}")
    public String getEditMonoAmp(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("monoAmpDetails")) {
            model.addAttribute("monoAmpDetails", monoAmpService.getDeviceDetails(id));
        }
        return "amplifiers/monoamp-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditMonoAmp(@Valid @ModelAttribute("monoAmpDetails") AddMonoAmpDTO addMonoAmpDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("monoAmpDetails", addMonoAmpDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "monoAmpDetails", bindingResult);
            return "redirect:/amplifiers/mono-amplifiers/edit/" + addMonoAmpDTO.getId();
        }
        return "redirect:/amplifiers/mono-amplifiers/" + monoAmpService.editDevice(addMonoAmpDTO);
    }

    @GetMapping("/{id}")
    public String monoAmpDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("monoAmpDetails", monoAmpService.getDeviceDetails(id));
        model.addAttribute("helperDTO", monoAmpService.getDeviceDetailsHelper(id));
        return "amplifiers/monoamp-details";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMonoAmp(@PathVariable("id") Long id) {
        monoAmpService.deleteDevice(id);
        return "redirect:/";
    }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("allDevices", monoAmpService.getAllDeviceSummary());
        return "amplifiers/monoamp-all";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable("id") Long id) {
        monoAmpService.likeDevice(id);
        return "redirect:/amplifiers/mono-amplifiers/rankings";
    }
}