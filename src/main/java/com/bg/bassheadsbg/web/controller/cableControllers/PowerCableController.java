package com.bg.bassheadsbg.web.controller.cableControllers;

import com.bg.bassheadsbg.model.dto.add.AddPowerCableDTO;
import com.bg.bassheadsbg.service.interfaces.PowerCableService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cables/power-cables")
public class PowerCableController {
    private final PowerCableService powerCableService;

    public PowerCableController(PowerCableService powerCableService) {
        this.powerCableService = powerCableService;
    }

    @GetMapping("/add")
    public String addCable(Model model) {
        if (!model.containsAttribute("addPowerCableDTO")) {
            model.addAttribute("addPowerCableDTO", powerCableService.createNewCableDTO());
        }
        return "cables/powercable-add";
    }

    @PostMapping("/add")
    public String addCable(@Valid @ModelAttribute("addPowerCableDTO") AddPowerCableDTO addPowerCableDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPowerCableDTO", addPowerCableDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPowerCableDTO", bindingResult);
            return "redirect:/cables/power-cables/add";
        }
        return "redirect:/cables/power-cables/" + powerCableService.addCable(addPowerCableDTO);
    }

    @GetMapping("/edit/{id}")
    public String getEditCable(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("cableDetails")) {
            model.addAttribute("cableDetails", powerCableService.getCableDetails(id));
        }
        return "cables/powercable-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditCable(@Valid @ModelAttribute("cableDetails") AddPowerCableDTO addPowerCableDTO,
                                BindingResult bindingResult,
                                @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
                                RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("cableDetails", addPowerCableDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "cableDetails", bindingResult);
            return "redirect:/cables/power-cables/edit/" + addPowerCableDTO.getId();
        }

        long cableId = powerCableService.editCable(addPowerCableDTO, imageFiles);
        return "redirect:/cables/power-cables/" + cableId;
    }

    @GetMapping("/{id}")
    public String cableDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cableDetails", powerCableService.getCableDetails(id));
        model.addAttribute("helperDTO", powerCableService.getPowerCableDetailsHelper(id));
        return "cables/powercable-details";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCable(@PathVariable("id") Long id) {
        powerCableService.deleteCable(id);
        return "redirect:/";
    }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("allCables", powerCableService.getAllPowerCablesSummarySorted());
        return "cables/powercable-all";
    }

    @PostMapping("/like/{id}")
    public String likeCable(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean likePowerCableSuccess = powerCableService.likeCable(id);

        if (!likePowerCableSuccess) {
            redirectAttributes.addFlashAttribute("powerCableAlreadyLikedId", id);
        }
        return "redirect:/cables/power-cables/rankings";
    }
}