package com.bg.bassheadsbg.web.controller.userControllers;

import com.bg.bassheadsbg.service.interfaces.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class AllUsersController {
    private final UserService userService;

    public AllUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "/users/all-users";
    }

    @PostMapping("/add-role/{userId}/{role}")
    public String addRole(@PathVariable Long userId,
                          @PathVariable String role,
                          @AuthenticationPrincipal UserDetails userDetails) {
        userService.addRoleToUserId(role, userId);
        return "redirect:/users/all";
    }

    @PostMapping("/remove-role/{userId}/{role}")
    public String removeRole(@PathVariable Long userId,
                             @PathVariable String role,
                             @AuthenticationPrincipal UserDetails userDetails) {
        userService.removeRoleToUserId(role, userId);
        return "redirect:/users/all";
    }

    @PostMapping("/disable/{userId}")
    public String disableUser(@PathVariable Long userId) {
        userService.disableUser(userId);
        return "redirect:/users/all";
    }

    @PostMapping("/enable/{userId}")
    public String enableUser(@PathVariable Long userId) {
        userService.enableUser(userId);
        return "redirect:/users/all";
    }
}