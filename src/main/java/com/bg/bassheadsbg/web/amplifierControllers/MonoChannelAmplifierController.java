package com.bg.bassheadsbg.web.amplifierControllers;


import com.bg.bassheadsbg.model.dto.AddMonoAmpDTO;
import com.bg.bassheadsbg.service.MonoAmpService;
import com.bg.bassheadsbg.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
            model.addAttribute("addMonoAmpDTO", new AddMonoAmpDTO());
        }
        return "/amps/monoamp-add";
    }

    @ModelAttribute("addMonoAmpDTO")
    public AddMonoAmpDTO addMonoAmpDTO() {
        return new AddMonoAmpDTO();
    }

    @PostMapping("/add")
    public String addMonoAmp(@Valid @ModelAttribute("addMonoAmpDTO") AddMonoAmpDTO addMonoAmpDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMonoAmpDTO", addMonoAmpDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMonoAmpDTO", bindingResult);
            return "redirect:/amplifiers/mono-amplifiers/add";
        }

        long newMonoAmp = monoAmpService.addDevice(addMonoAmpDTO);
        return "redirect:/amplifiers/mono-amplifiers/" + newMonoAmp;
    }

    @GetMapping("/{id}")
    public String monoAmpDetails(@PathVariable("id") Long id,
                                   Model model) {
        model.addAttribute("monoAmpDetails", monoAmpService.getDeviceDetails(id));
        return "/amps/monoamp-details";
    }

    @DeleteMapping("/{id}")
    public String deleteMonoAmp(@PathVariable("id") Long id) {
        monoAmpService.deleteDevice(id);
        return "redirect:/";
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleObjectNotFound(ObjectNotFoundException onfe) {
        ModelAndView modelAndView = new ModelAndView("/error/not-found");
        modelAndView.addObject("name", onfe.getId());
        return modelAndView;
    }
}