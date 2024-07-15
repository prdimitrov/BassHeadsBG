package com.bg.bassheadsbg.web.amplifierControllers;


import com.bg.bassheadsbg.model.dto.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.service.MultiChannelAmpService;
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
@RequestMapping("/amplifiers/multichannel-amplifiers")
public class MultiChannelAmplifierController {
    private final MultiChannelAmpService multiChannelAmpService;

    public MultiChannelAmplifierController(MultiChannelAmpService multiChannelAmpService) {
        this.multiChannelAmpService = multiChannelAmpService;
    }

    @GetMapping("/add")
    public String addMultiChannelAmp(Model model) {
        if (!model.containsAttribute("addMultiChannelAmpDTO")) {
            model.addAttribute("addMultiChannelAmpDTO", new AddMultiChannelAmpDTO());
        }
        return "/amps/multichannel-amp-add";
    }

    @ModelAttribute("addMultiChannelAmpDTO")
    public AddMultiChannelAmpDTO addMultiChannelAmpDTO() {
        return new AddMultiChannelAmpDTO();
    }

    @PostMapping("/add")
    public String addMultiChannelAmp(@Valid @ModelAttribute("addMultiChannelAmpDTO") AddMultiChannelAmpDTO addMultiChannelAmpDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMultiChannelAmpDTO", addMultiChannelAmpDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMultiChannelAmpDTO", bindingResult);
            return "redirect:/amplifiers/multi-channel-amplifiers/add";
        }

        long newMultiChannelAmp = multiChannelAmpService.addDevice(addMultiChannelAmpDTO);
        return "redirect:/amplifiers/multi-channel-amplifiers/" + newMultiChannelAmp;
    }

    @GetMapping("/{id}")
    public String multiChannelAmpDetails(@PathVariable("id") Long id,
                                 Model model) {
        model.addAttribute("multiChannelAmpDetails", multiChannelAmpService.getDeviceDetails(id));
        return "/amps/multi-channel-details";
    }

    @DeleteMapping("/{id}")
    public String deleteMultiChannelAmp(@PathVariable("id") Long id) {
        multiChannelAmpService.deleteDevice(id);
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