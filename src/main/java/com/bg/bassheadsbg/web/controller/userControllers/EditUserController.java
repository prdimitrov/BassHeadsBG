package com.bg.bassheadsbg.web.controller.userControllers;

import com.bg.bassheadsbg.model.dto.UserEntityEditDTO;
import com.bg.bassheadsbg.service.interfaces.CityService;
import com.bg.bassheadsbg.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class EditUserController {
    private final UserService userService;
    private final CityService cityService;

    public EditUserController(UserService userService, CityService cityService) {
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping("/edit/{userId}")
    public String getEditUser(@PathVariable("userId") Long userId, Model model) {
        if (!model.containsAttribute("editDTO")) {
            model.addAttribute("editDTO", userService.getUserDetails(userId));
        }
        model.addAttribute("cities", cityService.getAllCities());
        return "users/user-edit";
    }

    @PostMapping("/edit/{userId}")
    public String postEditUser(@Valid @ModelAttribute("editDTO") UserEntityEditDTO userEntityEditDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editDTO", userEntityEditDTO);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "editDTO", bindingResult);
            return "redirect:/users/edit/" + userEntityEditDTO.getId();
        }

        userService.updateUser(userEntityEditDTO);

        return "redirect:/users/edit/" + userEntityEditDTO.getId();
    }
}
