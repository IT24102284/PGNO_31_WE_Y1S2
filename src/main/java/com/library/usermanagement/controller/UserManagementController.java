package com.library.usermanagement.controller;

import com.library.usermanagement.model.RegularUser;
import com.library.usermanagement.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserManagementController {

    @Autowired
    private UserManagementService service;

    // Show Registration Form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegularUser());
        return "usermanagement/register";
    }

    // Handle Registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegularUser user, Model model) {
        service.registerUser(user);
        return "redirect:/users/login";
    }

    // Show Login Form
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new RegularUser());
        return "usermanagement/login";
    }

    // Handle Login
    @PostMapping("/login")
    public String loginUser(@ModelAttribute RegularUser user, Model model) {
        RegularUser loggedIn = service.login(user.getUsername(), user.getPassword());
        if (loggedIn != null) {
            model.addAttribute("user", loggedIn);
            return "usermanagement/profile"; // create profile view
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "usermanagement/login";
        }
    }

    // View All Users (Admin)
    @GetMapping("/list")
    public String listAllUsers(Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "usermanagement/list";
    }

    // View Single User by ID
    @GetMapping("/{id}")
    public String viewUser(@PathVariable String id, Model model) {
        RegularUser user = service.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "usermanagement/profile";
        }
        return "redirect:/users/list";
    }

    // Show Update Form
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        RegularUser user = service.findById(id);
        model.addAttribute("user", user);
        return "usermanagement/update";
    }

    // Handle Update
    @PostMapping("/update")
    public String updateUser(@ModelAttribute RegularUser user) {
        service.updateUser(user);
        return "redirect:/users/list";
    }

    // Delete User
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return "redirect:/users/list";
    }
}
