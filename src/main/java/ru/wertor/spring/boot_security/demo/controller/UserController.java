package ru.wertor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wertor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String showUserFromId(Model model) {
        model.addAttribute("user", userService.findById(userService.loggedUserId()));
        return "user";
    }
}