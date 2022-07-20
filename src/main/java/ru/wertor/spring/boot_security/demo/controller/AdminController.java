package ru.wertor.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wertor.spring.boot_security.demo.model.Role;
import ru.wertor.spring.boot_security.demo.model.User;
import ru.wertor.spring.boot_security.demo.repository.RoleRepository;
import ru.wertor.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/users")
    public String showUsers(Model model, @AuthenticationPrincipal User user) {
//        model.addAttribute("user", user);
        model.addAttribute("user", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/add")
    public String addNewUser(@ModelAttribute("user") User user, Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "admin/add";
    }

    @PostMapping("/add")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/{id}")
    public String showUserFromId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/user";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roles);
        return "admin/edit";
    }

    @PatchMapping(value = "/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }
}
