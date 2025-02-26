package com.bg.bassheadsbg.web.controller.speakerControllers;

import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.service.interfaces.MidRangeService;
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
@RequestMapping("/speakers/mid-range")
public class MidRangeController {
    private final MidRangeService midRangeService;

    public MidRangeController(MidRangeService midRangeService) {
        this.midRangeService = midRangeService;
    }

    @GetMapping("/add")
    public String addMidRange(Model model) {
        if (!model.containsAttribute("addMidRangeDTO")) {
            model.addAttribute("addMidRangeDTO", midRangeService.createNewSpeaker());
        }
        return "speakers/midrange-add";
    }

    @PostMapping("/add")
    public String addMidRange(@Valid @ModelAttribute("addMidRangeDTO") AddMidRangeDTO addMidRangeDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMidRangeDTO", addMidRangeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMidRangeDTO", bindingResult);
            return "redirect:/speakers/mid-range/add";
        }
        return "redirect:/speakers/mid-range/" + midRangeService.addSpeaker(addMidRangeDTO);
    }

    @GetMapping("/edit/{id}")
    public String getEditMidRange(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("midRangeDetails")) {
            model.addAttribute("midRangeDetails", midRangeService.getSpeakerDetails(id));
        }
        return "speakers/midrange-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditMidRange(@Valid @ModelAttribute("midRangeDetails") AddMidRangeDTO addMidRangeDTO,
                                   BindingResult bindingResult,
                                   @RequestParam(required = false) List<MultipartFile> imageFiles,
                                   RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("midRangeDetails", addMidRangeDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "midRangeDetails", bindingResult);
            return "redirect:/speakers/mid-range/edit/" + addMidRangeDTO.getId();
        }
        return "redirect:/speakers/mid-range/" + midRangeService.editSpeaker(addMidRangeDTO, imageFiles);
    }

    @GetMapping("/{id}")
    public String midRangeDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("midRangeDetails", midRangeService.getSpeakerDetails(id));
        model.addAttribute("helperDTO", midRangeService.getSpeakerDetailsHelper(id));
        return "speakers/midrange-details";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMidRange(@PathVariable("id") Long id) {
        midRangeService.deleteSpeaker(id);
        return "redirect:/";
    }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("allDevices", midRangeService.getAllSpeakerSummary());
        return "speakers/midrange-all";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable("id") Long id) {
        midRangeService.likeSpeaker(id);
        return "redirect:/speakers/mid-range/rankings";
    }
}