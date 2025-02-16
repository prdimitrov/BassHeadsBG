package com.bg.bassheadsbg.web.controller.otherControllers;

import com.bg.bassheadsbg.model.dto.add.AddCableDTO;
import com.bg.bassheadsbg.service.interfaces.CableService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cables")
public class CableController {
    private final CableService cableService;

    public CableController(CableService cableService) {
        this.cableService = cableService;
    }

    @GetMapping("/add")
    public String addCable(Model model) {
        if (!model.containsAttribute("addCableDTO")) {
            model.addAttribute("addCableDTO", cableService.createNewCableDTO());
        }
        return "cables/cable-add";
    }

    @PostMapping("/add")
    public String addCable(@Valid @ModelAttribute("addCableDTO") AddCableDTO addCableDTO,
                           @RequestParam("imageFiles") List<MultipartFile> imageFiles,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCableDTO", addCableDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCableDTO", bindingResult);
            return "redirect:/cables/add";
        }
        return "redirect:/cables/" + cableService.addCable(addCableDTO, imageFiles);
    }

    @GetMapping("/{cableId}/images")
    public ResponseEntity<List<byte[]>> getCableImages(@PathVariable Long cableId) {
        List<byte[]> imageFiles = cableService.getCableImages(cableId);
        if (imageFiles != null) {
            return new ResponseEntity<>(imageFiles, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/edit/{id}")
    public String getEditCable(@PathVariable("id") Long id, Model model) {
        if (!model.containsAttribute("cableDetails")) {
            model.addAttribute("cableDetails", cableService.getCableDetails(id));
        }
        return "cables/cable-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditCable(@Valid @ModelAttribute("cableDetails") AddCableDTO addCableDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("cableDetails", addCableDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "cableDetails", bindingResult);
            return "redirect:/cables/edit" + addCableDTO.getId();
        }
        return "redirect:/cables/" + cableService.editCable(addCableDTO);
    }

    @GetMapping("/{id}")
    public String cableDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cableDetails", cableService.getCableDetails(id));
        return "cables/cable-details";
    }

    @DeleteMapping("/{id}")
    public String deleteCable(@PathVariable("id") Long id) {
        cableService.deleteCable(id);
        return "redirect:/";
    }
}
