package com.bg.bassheadsbg.web.controller.amplifierControllers;


import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

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
            model.addAttribute("addMonoAmpDTO", monoAmpService.createNewAmplifier());
        }
        return "amplifiers/monoamp-add";
    }

    @PostMapping("/add")
    public String addMonoAmp(@Valid @ModelAttribute("addMonoAmpDTO") AddMonoAmpDTO addMonoAmpDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMonoAmpDTO", addMonoAmpDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMonoAmpDTO", bindingResult);
            return "redirect:/amplifiers/mono-amplifiers/add";
        }
        return "redirect:/amplifiers/mono-amplifiers/" + monoAmpService.addAmplifier(addMonoAmpDTO);
    }

    @GetMapping("/edit/{id}")
    public String getEditMonoAmp(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("monoAmpDetails")) {
            model.addAttribute("monoAmpDetails", monoAmpService.getAmplifierDetails(id));
        }
        return "amplifiers/monoamp-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditMonoAmp(@Valid @ModelAttribute("monoAmpDetails") AddMonoAmpDTO addMonoAmpDTO,
                                  BindingResult bindingResult,
                                  @RequestParam(required = false) List<MultipartFile> multipartFiles,
                                  RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("monoAmpDetails", addMonoAmpDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "monoAmpDetails", bindingResult);
            return "redirect:/amplifiers/mono-amplifiers/edit/" + addMonoAmpDTO.getId();
        }
        return "redirect:/amplifiers/mono-amplifiers/" + monoAmpService.editAmplifier(addMonoAmpDTO, multipartFiles);
    }

    @GetMapping("/{id}")
    public String monoAmpDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("monoAmpDetails", monoAmpService.getAmplifierDetails(id));
        model.addAttribute("helperDTO", monoAmpService.getAmplifierDetailsHelper(id));
        return "amplifiers/monoamp-details";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMonoAmp(@PathVariable("id") Long id) {
        monoAmpService.deleteAmplifier(id);
        return "redirect:/";
    }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("allDevices", monoAmpService.getAllAmplifiersSummarySorted());
        return "amplifiers/monoamp-all";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable("id") Long id) {
        monoAmpService.likeAmplifier(id);
        return "redirect:/amplifiers/mono-amplifiers/rankings";
    }
}