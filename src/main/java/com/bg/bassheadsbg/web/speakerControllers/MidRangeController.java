package com.bg.bassheadsbg.web.speakerControllers;

import com.bg.bassheadsbg.model.dto.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.helpers.MidRangeDetailsHelperDTO;
import com.bg.bassheadsbg.service.MidRangeService;
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
@RequestMapping("/speakers/mid-range")
public class MidRangeController {
    private final MidRangeService midRangeService;

    public  MidRangeController(MidRangeService midRangeService) {
        this.midRangeService = midRangeService;
    }

    @GetMapping("/add")
    public String addMidRange(Model model) {
        if (!model.containsAttribute("addMidRangeDTO")) {
            model.addAttribute("addMidRangeDTO", new AddMidRangeDTO());
        }
        return "/speakers/midrange-add";
    }

    @ModelAttribute("addMidRangeDTO")
    public AddMidRangeDTO addMidRangeDTO() {
        return new AddMidRangeDTO();
    }

    @PostMapping("/add")
    public String addMidRange(@Valid @ModelAttribute("addMidRangeDTO") AddMidRangeDTO addMidRangeDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMidRangeDTO", addMidRangeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMidRangeDTO", bindingResult);
            return "redirect:/speakers/mid-range/add";
        }

        long newMidRange = midRangeService.addDevice(addMidRangeDTO);
        return "redirect:/speakers/mid-range/" + newMidRange;
    }

    @GetMapping("/edit/{id}")
    public String getEditMidRange(@PathVariable("id") Long id,
                                   Model model) {
        MidRangeDetailsDTO midRangeDetailsDTO = midRangeService.getDeviceDetails(id);

        if (!model.containsAttribute("midRangeDetails")) {
            model.addAttribute("midRangeDetails", midRangeDetailsDTO);
        }
        return "/speakers/midrange-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditMidRange(@Valid @ModelAttribute("midRangeDetails") AddMidRangeDTO addMidRangeDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("midRangeDetails", addMidRangeDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "midRangeDetails", bindingResult);
            return "redirect:/speakers/mid-range/edit/" + addMidRangeDTO.getId();
        }

        long newMidRange = midRangeService.addDevice(addMidRangeDTO);
        return "redirect:/speakers/mid-range/" + newMidRange;
    }



    @GetMapping("/{id}")
    public String midRangeDetails(@PathVariable("id") Long id,
                                   Model model) {

        MidRangeDetailsDTO deviceDetails = midRangeService.getDeviceDetails(id);
        model.addAttribute("midRangeDetails", midRangeService.getDeviceDetails(id));

        MidRangeDetailsHelperDTO helperDTO =
                new MidRangeDetailsHelperDTO(deviceDetails);

        model.addAttribute("helperDTO", helperDTO);


        return "/speakers/midrange-details";
    }

    @DeleteMapping("/{id}")
    public String deleteMidRange(@PathVariable("id") Long id) {
        midRangeService.deleteDevice(id);
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