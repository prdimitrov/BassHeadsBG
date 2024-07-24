package com.bg.bassheadsbg.web.controller.speakerControllers;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceAlreadyLikedException;
import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
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
            model.addAttribute("addSubwooferDTO", subwooferService.createNewSubwooferDTO());
        }
        return "/speakers/subwoofer-add";
    }

    @PostMapping("/add")
    public String addSubwoofer(@Valid @ModelAttribute("addSubwooferDTO") AddSubwooferDTO addSubwooferDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addSubwooferDTO", addSubwooferDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addSubwooferDTO", bindingResult);
            return "redirect:/speakers/subwoofers/add";
        }

        try {
            return "redirect:/speakers/subwoofers/" + subwooferService.addDevice(addSubwooferDTO);
        } catch (JsonProcessingException | DeviceAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("addSubwooferDTO", addSubwooferDTO);
            return "redirect:/speakers/subwoofers/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String getEditSubwoofer(@PathVariable("id") Long id,
                                  Model model) {
        if (!model.containsAttribute("subwooferDetails")) {
            model.addAttribute("subwooferDetails", subwooferService.getDeviceDetails(id));
        }
        return "/speakers/subwoofer-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditSubwoofer(@Valid @ModelAttribute("subwooferDetails") AddSubwooferDTO addSubwooferDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("subwooferDetails", addSubwooferDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "subwooferDetails", bindingResult);
            return "redirect:/speakers/subwoofers/edit/" + addSubwooferDTO.getId();
        }

        return "redirect:/speakers/subwoofers/" + subwooferService.editDevice(addSubwooferDTO);
    }

    @GetMapping("/{id}")
    public String subwooferDetails(@PathVariable("id") Long id,
                                   Model model) {
        model.addAttribute("subwooferDetails", subwooferService.getDeviceDetails(id));
        model.addAttribute("helperDTO", subwooferService.getDeviceDetails(id));
        return "/speakers/subwoofer-details";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSubwoofer(@PathVariable("id") Long id) {
        subwooferService.deleteDevice(id);
        return "redirect:/";
    }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("allDevices", subwooferService.getAllDeviceSummary());
        return "/speakers/subwoofers-all";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable("id") Long id,
                       RedirectAttributes redirectAttributes) {
        try {
            subwooferService.likeDevice(id);
            return "redirect:/speakers/subwoofers/rankings";
        } catch (DeviceAlreadyLikedException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/speakers/subwoofers/rankings";
        }
    }

//    @ResponseStatus(code = HttpStatus.NOT_FOUND)
//    @ExceptionHandler(DeviceNotFoundException.class)
//    public ModelAndView handleObjectNotFound(DeviceNotFoundException onfe) {
//        ModelAndView modelAndView = new ModelAndView("/error/not-found");
//        modelAndView.addObject("name", onfe.getId());
//        return modelAndView;
//    }
}