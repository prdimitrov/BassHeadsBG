package com.bg.bassheadsbg.web.controller.speakerControllers;

import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.helpers.HighRangeDetailsHelperDTO;
import com.bg.bassheadsbg.service.interfaces.HighRangeService;
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
@RequestMapping("/speakers/high-range")
public class HighRangeController {
    private final HighRangeService highRangeService;

    public HighRangeController(HighRangeService highRangeService) {
        this.highRangeService = highRangeService;
    }

    @GetMapping("/add")
    public String addHighRange(Model model) {
        if (!model.containsAttribute("addHighRangeDTO")) {
            model.addAttribute("addHighRangeDTO", new AddHighRangeDTO());
        }
        return "/speakers/highrange-add";
    }

    @ModelAttribute("addHighRangeDTO")
    public AddHighRangeDTO addHighRangeDTO() {
        return new AddHighRangeDTO();
    }

    @PostMapping("/add")
    public String addHighRange(@Valid @ModelAttribute("addHighRangeDTO") AddHighRangeDTO addHighRangeDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addHighRangeDTO", addHighRangeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addHighRangeDTO", bindingResult);
            return "redirect:/speakers/high-range/add";
        }

        long newHighRange = highRangeService.addDevice(addHighRangeDTO);
        return "redirect:/speakers/high-range/" + newHighRange;
    }

    @GetMapping("/edit/{id}")
    public String getEditHighRange(@PathVariable("id") Long id,
                                Model model) {
        HighRangeDetailsDTO highRangeDetailsDTO = highRangeService.getDeviceDetails(id);

        if (!model.containsAttribute("highRangeDetails")) {
            model.addAttribute("highRangeDetails", highRangeDetailsDTO);
        }
        return "/speakers/highrange-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditHighRange(@Valid @ModelAttribute("highRangeDetails") AddHighRangeDTO addHighRangeDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("highRangeDetails", addHighRangeDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "highRangeDetails", bindingResult);
            return "redirect:/speakers/high-range/edit/" + addHighRangeDTO.getId();
        }

        long newHighRange = highRangeService.editDevice(addHighRangeDTO);
        return "redirect:/speakers/high-range/" + newHighRange;
    }



    @GetMapping("/{id}")
    public String highRangeDetails(@PathVariable("id") Long id,
                                   Model model) {

        HighRangeDetailsDTO deviceDetails = highRangeService.getDeviceDetails(id);
        model.addAttribute("highRangeDetails", highRangeService.getDeviceDetails(id));

        HighRangeDetailsHelperDTO helperDTO =
                new HighRangeDetailsHelperDTO(deviceDetails);

        model.addAttribute("helperDTO", helperDTO);


        return "/speakers/highrange-details";
    }

    @DeleteMapping("/{id}")
    public String deleteHighRange(@PathVariable("id") Long id) {
        highRangeService.deleteDevice(id);
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