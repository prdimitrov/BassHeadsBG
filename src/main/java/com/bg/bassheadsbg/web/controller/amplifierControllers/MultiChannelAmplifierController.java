package com.bg.bassheadsbg.web.controller.amplifierControllers;


import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.helpers.MultiChannelAmpDetailsHelperDTO;
import com.bg.bassheadsbg.service.interfaces.MultiChannelAmpService;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            model.addAttribute("addMultiChannelAmpDTO", new AddMultiChannelAmpDTO());
        }
        return "/amplifiers/multichannel-amp-add";
    }

    @ModelAttribute("addMultiChannelAmpDTO")
    public AddMultiChannelAmpDTO addMultiChannelAmpDTO() {
        return new AddMultiChannelAmpDTO();
    }

    @PostMapping("/add")
    public String addMultiChannelAmp(@Valid @ModelAttribute("addMultiChannelAmpDTO") AddMultiChannelAmpDTO addMultiChannelAmpDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMultiChannelAmpDTO", addMultiChannelAmpDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMultiChannelAmpDTO", bindingResult);
            return "redirect:/amplifiers/multi-channel-amplifiers/add";
        }

        long newMultiChannelAmp = multiChannelAmpService.addDevice(addMultiChannelAmpDTO);
        return "redirect:/amplifiers/multi-channel-amplifiers/" + newMultiChannelAmp;
    }

    @GetMapping("/edit/{id}")
    public String getEditMultiChannelAmp(@PathVariable("id") Long id,
                                 Model model) {

        if (!model.containsAttribute("multiChannelAmpDetails")) {
            MultiChannelAmpDetailsDTO multiChannelAmpDetailsDTO = multiChannelAmpService.getDeviceDetails(id);
            model.addAttribute("multiChannelAmpDetails", multiChannelAmpDetailsDTO);
        }
        return "/amplifiers/multichannel-amp-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditMultiChannelAmp(@Valid @ModelAttribute("multiChannelAmpDetails") AddMultiChannelAmpDTO addMultiChannelAmpDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("multiChannelAmpDetails", addMultiChannelAmpDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "multiChannelAmpDetails", bindingResult);
            return "redirect:/amplifiers/multi-channel-amplifiers/edit/" + addMultiChannelAmpDTO.getId();
        }

        long newMultiChannelAmp = multiChannelAmpService.editDevice(addMultiChannelAmpDTO);
        return "redirect:/amplifiers/multi-channel-amplifiers/" + newMultiChannelAmp;
    }

    @GetMapping("/{id}")
    public String multiChannelAmpDetails(@PathVariable("id") Long id,
                                 Model model) {

        MultiChannelAmpDetailsDTO deviceDetails = multiChannelAmpService.getDeviceDetails(id);
        model.addAttribute("multiChannelAmpDetails", multiChannelAmpService.getDeviceDetails(id));

        MultiChannelAmpDetailsHelperDTO helperDTO =
                new MultiChannelAmpDetailsHelperDTO(deviceDetails);

        model.addAttribute("helperDTO", helperDTO);

        model.addAttribute("multiChannelAmpDetails", multiChannelAmpService.getDeviceDetails(id));
        return "/amplifiers/multichannel-amp-details";
    }

    @DeleteMapping("/{id}")
    public String deleteMultiChannelAmp(@PathVariable("id") Long id) {
        multiChannelAmpService.deleteDevice(id);
        return "redirect:/";
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(DeviceNotFoundException.class)
    public ModelAndView handleObjectNotFound(DeviceNotFoundException onfe) {
        ModelAndView modelAndView = new ModelAndView("/error/not-found");
        modelAndView.addObject("name", onfe.getId());
        return modelAndView;
    }
}