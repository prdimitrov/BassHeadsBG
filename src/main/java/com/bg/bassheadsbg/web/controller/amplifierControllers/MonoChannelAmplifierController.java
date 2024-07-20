package com.bg.bassheadsbg.web.controller.amplifierControllers;


import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.helpers.MonoAmpDetailsHelperDTO;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
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
        return "/amplifiers/monoamp-add";
    }
//    th:href="@{/amplifiers/mono-amplifiers/rankings}"

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("allMonoAmps", monoAmpService.getAllDeviceSummary());
        return "/amplifiers/monoamp-all";
    }

    @ModelAttribute("addMonoAmpDTO")
    public AddMonoAmpDTO addMonoAmpDTO() {
        return new AddMonoAmpDTO();
    }

    @PostMapping("/add")
    public String addMonoAmp(@Valid @ModelAttribute("addMonoAmpDTO") AddMonoAmpDTO addMonoAmpDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addMonoAmpDTO", addMonoAmpDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addMonoAmpDTO", bindingResult);
            return "redirect:/amplifiers/mono-amplifiers/add";
        }

        long newMonoAmp = monoAmpService.addDevice(addMonoAmpDTO);
        return "redirect:/amplifiers/mono-amplifiers/" + newMonoAmp;
    }

    @GetMapping("/edit/{id}")
    public String getEditMonoAmp(@PathVariable("id") Long id,
                                   Model model) {

        if (!model.containsAttribute("monoAmpDetails")) {
            MonoAmpDetailsDTO monoAmpDetailsDTO = monoAmpService.getDeviceDetails(id);
            model.addAttribute("monoAmpDetails", monoAmpDetailsDTO);
        }
        return "/amplifiers/monoamp-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditMonoAmp(@Valid @ModelAttribute("monoAmpDetails") AddMonoAmpDTO addMonoAmpDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("monoAmpDetails", addMonoAmpDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "monoAmpDetails", bindingResult);
            return "redirect:/amplifiers/mono-amplifiers/edit/" + addMonoAmpDTO.getId();
        }

        long newMonoAmp = monoAmpService.editDevice(addMonoAmpDTO);
        return "redirect:/amplifiers/mono-amplifiers/" + newMonoAmp;
    }

    @GetMapping("/{id}")
    public String monoAmpDetails(@PathVariable("id") Long id,
                                   Model model) {

        MonoAmpDetailsDTO deviceDetails = monoAmpService.getDeviceDetails(id);
        model.addAttribute("monoAmpDetails", monoAmpService.getDeviceDetails(id));

        MonoAmpDetailsHelperDTO helperDTO =
                new MonoAmpDetailsHelperDTO(deviceDetails);

        model.addAttribute("helperDTO", helperDTO);
        return "/amplifiers/monoamp-details";
    }

    @DeleteMapping("/{id}")
    public String deleteMonoAmp(@PathVariable("id") Long id) {
        monoAmpService.deleteDevice(id);
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