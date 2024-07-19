package com.bg.bassheadsbg.web.controller.speakerControllers;

import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.helpers.SubwooferDetailsHelperDTO;
import com.bg.bassheadsbg.service.interfaces.SubwooferService;
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
@RequestMapping("/speakers/subwoofers")
public class SubwooferController {
    private final SubwooferService subwooferService;

    public SubwooferController(SubwooferService subwooferService) {
        this.subwooferService = subwooferService;
    }

    @GetMapping("/add")
    public String addSubwoofer(Model model) {
        if (!model.containsAttribute("addSubwooferDTO")) {
            model.addAttribute("addSubwooferDTO", new AddSubwooferDTO());
        }
        return "/speakers/subwoofer-add";
    }

    @ModelAttribute("addSubwooferDTO")
    public AddSubwooferDTO addSubwooferDTO() {
        return new AddSubwooferDTO();
    }

    @PostMapping("/add")
    public String addSubwoofer(@Valid @ModelAttribute("addSubwooferDTO") AddSubwooferDTO addSubwooferDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addSubwooferDTO", addSubwooferDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addSubwooferDTO", bindingResult);
            return "redirect:/speakers/subwoofers/add";
        }

        long newSubwoofer = subwooferService.addDevice(addSubwooferDTO);
        return "redirect:/speakers/subwoofers/" + newSubwoofer;
    }

    @GetMapping("/edit/{id}")
    public String getEditSubwoofer(@PathVariable("id") Long id,
                                  Model model) {
        SubwooferDetailsDTO subwooferDetailsDTO = subwooferService.getDeviceDetails(id);

        if (!model.containsAttribute("subwooferDetails")) {
            model.addAttribute("subwooferDetails", subwooferDetailsDTO);
        }
        return "/speakers/subwoofer-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditSubwoofer(@Valid @ModelAttribute("subwooferDetails") AddSubwooferDTO addSubwooferDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("subwooferDetails", addSubwooferDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "subwooferDetails", bindingResult);
            return "redirect:/speakers/subwoofers/edit/" + addSubwooferDTO.getId();
        }

        long newSubwoofer = subwooferService.editDevice(addSubwooferDTO);
        return "redirect:/speakers/subwoofers/" + newSubwoofer;
    }

    @GetMapping("/{id}")
    public String subwooferDetails(@PathVariable("id") Long id,
                                   Model model) {

        SubwooferDetailsDTO deviceDetails = subwooferService.getDeviceDetails(id);
        model.addAttribute("subwooferDetails", subwooferService.getDeviceDetails(id));

        SubwooferDetailsHelperDTO helperDTO =
                new SubwooferDetailsHelperDTO(deviceDetails);

        model.addAttribute("helperDTO", helperDTO);
        return "/speakers/subwoofer-details";
    }

    @DeleteMapping("/{id}")
    public String deleteSubwoofer(@PathVariable("id") Long id) {
        subwooferService.deleteDevice(id);
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