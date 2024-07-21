package com.bg.bassheadsbg.web.controller.userControllers;

import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.service.interfaces.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class AllUsersController {
    private final UserService userService;

    public AllUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String allUsers(Model model) {
        List<UserEntity> users = userService.findAllUsers();
        model.addAttribute("users", users);
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

    @PostMapping("/ban/{userId}")
    public String banUser(@PathVariable Long userId) {
        userService.banUser(userId);
        return "redirect:/users/all";
    }

    @PostMapping("/unban/{userId}")
    public String unbanUser(@PathVariable Long userId) {
        userService.unbanUser(userId);
        return "redirect:/users/all";
    }
}
