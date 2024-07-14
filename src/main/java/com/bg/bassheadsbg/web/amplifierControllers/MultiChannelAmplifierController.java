package com.bg.bassheadsbg.web.amplifierControllers;


import com.bg.bassheadsbg.model.dto.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.service.MultiChannelAmpService;
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
public class MultiChannelAmplifierController {
    private final MultiChannelAmpService multiChannelAmpService;

    public MultiChannelAmplifierController(MultiChannelAmpService multiChannelAmpService) {
        this.multiChannelAmpService = multiChannelAmpService;
    }

    @GetMapping("/add-multichannelamp")
    public String addMultiChannelAmp() {

        return "multichannelamp-add";
    }

    @ModelAttribute("addMultiChannelAmpDTO")
    public AddMultiChannelAmpDTO addMultiChannelAmpDTO() {
        return new AddMultiChannelAmpDTO();
    }

    @PostMapping("/add-multichannelamp")
    public String addMultiChannelAmp(@Valid AddMultiChannelAmpDTO addMultiChannelAmpDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMultiChannelAmpDTO", new AddMultiChannelAmpDTO());
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addMultiChannelAmpDTO"
                    , bindingResult);
            return "redirect:/amplifiers/add-multichannelamp";
        }

        multiChannelAmpService.addDevice(addMultiChannelAmpDTO);
        return "redirect:/home";
    }

}
