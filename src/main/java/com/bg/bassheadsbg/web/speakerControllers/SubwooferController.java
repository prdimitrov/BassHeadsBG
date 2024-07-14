package com.bg.bassheadsbg.web.speakerControllers;


import com.bg.bassheadsbg.model.dto.AddSubwooferDTO;
import com.bg.bassheadsbg.service.SubwooferService;
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
        return "subwoofer-add";
    }

    @ModelAttribute("addSubwooferDTO")
    public AddSubwooferDTO addSubwooferDTO() {
        return new AddSubwooferDTO();
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

        long newSubwoofer = subwooferService.addDevice(addSubwooferDTO);
        return "redirect:/speakers/subwoofers/" + newSubwoofer;
    }

    @GetMapping("/{id}")
    public String subwooferDetails(@PathVariable("id") Long id,
                                   Model model) {
        model.addAttribute("subwooferDetails", subwooferService.getDeviceDetails(id));
        return "subwoofer-details";
    }

    @DeleteMapping("/{id}")
    public String deleteSubwoofer(@PathVariable("id") Long id) {
        subwooferService.deleteDevice(id);
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