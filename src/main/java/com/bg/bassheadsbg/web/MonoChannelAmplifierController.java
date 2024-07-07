package com.bg.bassheadsbg.web;

import com.bg.bassheadsbg.model.dto.AddMonoAmpDTO;
import com.bg.bassheadsbg.repository.MonoAmplifierRepository;
import com.bg.bassheadsbg.service.MonoAmpService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/amplifiers")
public class MonoChannelAmplifierController {
    private final MonoAmpService monoAmpService;

    public MonoChannelAmplifierController(MonoAmpService monoAmpService) {
        this.monoAmpService = monoAmpService;
    }

    @GetMapping("/add-monoamp")
    public String addMonoAmp() {

        return "monoamp-add";
    }

    @ModelAttribute("addMonoAmpDTO")
    public AddMonoAmpDTO addMonoAmpDTO() {
        return new AddMonoAmpDTO();
    }

    @PostMapping("/add-monoamp")
    public String addMonoAmp(@Valid AddMonoAmpDTO addMonoAmpDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMonoAmpDTO", new AddMonoAmpDTO());
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addMonoAmpDTO"
                    , bindingResult);
            return "redirect:/amplifiers/add-monoamp";
        }

        monoAmpService.addDevice(addMonoAmpDTO);
        return "redirect:/home";
    }

}
