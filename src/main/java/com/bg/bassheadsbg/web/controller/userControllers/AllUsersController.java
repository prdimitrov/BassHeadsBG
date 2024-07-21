package com.bg.bassheadsbg.web.controller.userControllers;

import com.bg.bassheadsbg.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/users/all")
    public String allUsers() {
        return "/users/all-users";
    }

    @PostMapping("/users/add-role/{userId}/{role}")
    public String addRole(@PathVariable Long userId,
                          @PathVariable String role,
                          @AuthenticationPrincipal UserDetails userDetails) {
        userService.addRoleToUserId(role, userId);
        return "redirect:/users/all";
    }

    @PostMapping("/users/remove-role/{userId}/{role}")
    public String removeRole(@PathVariable Long userId,
                             @PathVariable String role,
                             @AuthenticationPrincipal UserDetails userDetails) {
        userService.removeRoleToUserId(role, userId);
        return "redirect:/users/all";
    }
}
