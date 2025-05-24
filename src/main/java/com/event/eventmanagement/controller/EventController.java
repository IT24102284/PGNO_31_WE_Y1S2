package com.event.eventmanagement.controller;

import com.event.eventmanagement.model.Event;
import com.event.eventmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String home(Model model) throws IOException {
        List<Event> events = eventService.mergeSortByName(eventService.readEvents());
        model.addAttribute("events", events);
        return "index";
    }

    @GetMapping("/event/new")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("isEdit", false);
        return "event-form";
    }

    @PostMapping("/event/save")
    public String saveEvent(@ModelAttribute Event event) throws IOException {
        eventService.saveOrUpdateEvent(event);
        return "redirect:/";
    }

    @GetMapping("/event/edit/{name}")
    public String editEvent(@PathVariable String name, Model model) throws IOException {
        Event event = eventService.findByName(name);
        model.addAttribute("event", event);
        model.addAttribute("isEdit", true);
        return "event-form";
    }

    @GetMapping("/event/delete/{name}")
    public String deleteEvent(@PathVariable String name) throws IOException {
        eventService.deleteEvent(name);
        return "redirect:/";
    }
}
