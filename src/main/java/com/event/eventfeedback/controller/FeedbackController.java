package com.event.eventfeedback.controller;

// Import necessary classes and annotations
import com.event.eventfeedback.model.Feedback;
import com.event.eventfeedback.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller  // Marks this class as a Spring MVC controller
@RequestMapping("/")  // Base URL mapping for all request mappings in this controller
public class FeedbackController {

    private final FeedbackService service;

    // Constructor-based dependency injection for the FeedbackService
    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    // Handles GET requests to "/" and loads the feedback form with existing feedbacks
    @GetMapping("/")
    public String feedbackForm(Model model) throws IOException {
        model.addAttribute("feedback", new Feedback());  // Binds a new empty Feedback object for form
        model.addAttribute("feedbacks", service.getAllFeedbacks());  // Fetches all feedbacks to display
        return "feedback";  // Returns the Thymeleaf template named "feedback.html"
    }

    // Handles POST requests to "/submit" and saves the submitted feedback
    @PostMapping("/submit")
    public String submitFeedback(@ModelAttribute Feedback feedback) throws IOException {
        service.addFeedback(feedback);  // Adds new feedback via the service
        return "redirect:/";  // Redirects to home to display updated feedback list
    }

    // Handles GET requests to "/feedback/list" and displays all feedbacks in a separate view
    @GetMapping("/feedback/list")
    public String showFeedbackList(Model model) throws IOException {
        model.addAttribute("feedbackList", service.getAllFeedbacks());  // Fetches all feedbacks
        return "list";  // Returns the Thymeleaf template named "list.html"
    }
}
