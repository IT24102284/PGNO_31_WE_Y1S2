package com.library.usermanagement.controller;

import com.library.usermanagement.exception.DataAccessException;
import com.library.usermanagement.exception.DuplicateEmailException;
import com.library.usermanagement.exception.DuplicateUsernameException;
import com.library.usermanagement.exception.UserNotFoundException;
import com.library.usermanagement.model.RegularUser;
import com.library.usermanagement.service.UserManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserManagementController {

    @Autowired
    private UserManagementService service;

    // ─── Login (form) at GET /users ───────────────────────────────
    @GetMapping("")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new RegularUser());
        return "usermanagement/login";
    }

    // ─── Login (submit) at POST /users ───────────────────────────
    @PostMapping("")
    public String loginUser(
            @ModelAttribute("user") RegularUser user,
            HttpSession session,
            Model model) {

        try {
            RegularUser loggedIn = service.login(
                    user.getUsername(), user.getPassword()
            );
            // Store user ID for “/profile”
            session.setAttribute("currentUserId", loggedIn.getId());
            return "redirect:/users/profile";

        } catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            return "usermanagement/login";

        } catch (DataAccessException ex) {
            model.addAttribute("error", "Internal error – please try again later.");
            return "usermanagement/login";
        }
    }

    // ─── Registration form at GET /users/register ────────────────
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegularUser());
        return "usermanagement/register";
    }

    // ─── Registration submit at POST /users/register ─────────────
    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") RegularUser user,
            Model model) {

        try {
            service.registerUser(user);
            model.addAttribute("success", "Registration successful! Please log in.");
            return "usermanagement/login";

        } catch (DuplicateUsernameException ex) {
            model.addAttribute("errorUsername", ex.getMessage());

        } catch (DuplicateEmailException ex) {
            model.addAttribute("errorEmail", ex.getMessage());

        } catch (DataAccessException ex) {
            model.addAttribute("error", "Could not save your data – please try again.");
        }

        // on failure, re-show the form
        model.addAttribute("user", user);
        return "usermanagement/register";
    }

    // ─── List all users or, if 'query' provided, filtered ────────
    @GetMapping("/list")
    public String listAllUsers(
            @RequestParam(value = "query", required = false) String query,
            Model model) {

        List<RegularUser> users = (query != null && !query.isBlank())
                ? service.searchUsers(query.trim())
                : service.getAllUsers();

        model.addAttribute("users", users);
        if (query != null) {
            model.addAttribute("search", query);
        }
        return "usermanagement/list";
    }

    // ─── View single user by ID at GET /users/{id} ───────────────
    @GetMapping("/{id}")
    public String viewUser(@PathVariable String id, Model model) {
        RegularUser user = service.findById(id);
        model.addAttribute("user", user);
        return "usermanagement/profile";
    }

    // ─── Search by username (single‐user) ────────────────────────
    @GetMapping("/search")
    public String searchByUsername(@RequestParam String username, Model model) {
        RegularUser user = service.findByUsername(username);
        model.addAttribute("user", user);
        return "usermanagement/profile";
    }

    // ─── Show “my profile” via session ID at GET /users/profile ────
    @GetMapping("/profile")
    public String showOwnProfile(HttpSession session, Model model) {
        String id = (String) session.getAttribute("currentUserId");
        if (id == null) {
            model.addAttribute("error", "No user found. Please log in.");
            return "error";
        }
        RegularUser user = service.findById(id);
        model.addAttribute("user", user);
        return "usermanagement/profile";
    }

    // ─── Show update form at GET /users/update/{id} ──────────────
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        RegularUser user = service.findById(id);
        model.addAttribute("user", user);
        return "usermanagement/update";
    }

    // ─── Handle update at POST /users/update ────────────────────
    @PostMapping("/update")
    public String updateUserForm(
            @ModelAttribute("user") RegularUser updates,
            Model model) {

        try {
            RegularUser updated = service.updateUserPartially(updates.getId(), updates);
            model.addAttribute("user", updated);
            model.addAttribute("success", "Profile updated successfully");
            return "usermanagement/profile";

        } catch (DuplicateUsernameException ex) {
            model.addAttribute("errorUsername", ex.getMessage());

        } catch (DuplicateEmailException ex) {
            model.addAttribute("errorEmail", ex.getMessage());

        } catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("users", service.getAllUsers());
            return "usermanagement/list";

        } catch (DataAccessException ex) {
            model.addAttribute("error", "Unable to update profile right now.");
        }

        model.addAttribute("user", updates);
        return "usermanagement/update";
    }

    // ─── Delete user at GET /users/delete/{id} ──────────────────
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id, Model model) {
        try {
            service.deleteUser(id);
            model.addAttribute("success", "User deleted successfully");
        } catch (Exception ex) {
            model.addAttribute("error", "Failed to delete user: " + ex.getMessage());
        }
        model.addAttribute("users", service.getAllUsers());
        return "usermanagement/list";
    }

    // ─── Handle “not found” anywhere under /users ──────────────
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("users", service.getAllUsers());
        return "usermanagement/list";
    }

    // ─── Handle storage errors (file I/O) ─────────────────────
    @ExceptionHandler(DataAccessException.class)
    public String handleStorageError(DataAccessException ex, Model model) {
        model.addAttribute("error", "Sorry—there was a server error. Please try again later.");
        return "error";
    }

    // ─── Catch-all fallback ─────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public String handleOtherErrors(Exception ex, Model model) {
        model.addAttribute("error", "An unexpected error occurred.");
        return "error";
    }
}
