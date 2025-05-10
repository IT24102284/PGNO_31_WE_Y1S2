package com.event.eventmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
public class EventManagementController {

    @GetMapping
    public String showAddEventPage() {
        return "eventmanagement/addevent";
    }
}
