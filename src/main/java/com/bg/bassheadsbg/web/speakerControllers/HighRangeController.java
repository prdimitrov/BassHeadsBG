package com.bg.bassheadsbg.web.speakerControllers;

import com.bg.bassheadsbg.model.dto.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.HighRangeDetailsDTO;
import com.bg.bassheadsbg.service.HighRangeService;
import com.bg.bassheadsbg.service.exception.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addHighRangeDTO", addHighRangeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addHighRangeDTO", bindingResult);
            return "redirect:/speakers/high-range/add";
        }

        long newHighRange = highRangeService.addDevice(addHighRangeDTO);
        return "redirect:/speakers/high-range/" + newHighRange;
    }

    @GetMapping("/{id}")
    public String highRangeDetails(@PathVariable("id") Long id,
                                   Model model) {
        model.addAttribute("highRangeDetails", highRangeService.getDeviceDetails(id));
        return "/speakers/highrange-details";
    }

    @DeleteMapping("/{id}")
    public String deleteHighRange(@PathVariable("id") Long id) {
        highRangeService.deleteDevice(id);
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