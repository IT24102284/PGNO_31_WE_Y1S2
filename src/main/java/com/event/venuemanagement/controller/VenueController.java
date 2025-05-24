package com.event.venuemanagement.controller;

import com.event.venuemanagement.model.Venue;
import com.event.venuemanagement.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/venues")  // Base path for all venue URLs
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping("/")
    public String redirectToVenues() {
        return "redirect:/venues";
    }

    // 1. List all venues - URL: /venues
    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("venues", venueService.getAllVenues());
        return "index";  // your Thymeleaf template for listing venues
    }

    // 2. Show add venue form - URL: /venues/add
    @GetMapping("/add")
    public String showAddVenueForm(Model model) {
        model.addAttribute("venue", new Venue());  // fix attribute name to "venue"
        return "addVenue";
    }

    // 3. Save new venue - URL: /venues/save
    @PostMapping("/save")
    public String saveVenue(@ModelAttribute("venue") Venue venue) {
        venueService.addVenue(venue);
        return "redirect:/venues";
    }

    // 4. Show edit venue form - URL: /venues/edit/{id}
    @GetMapping("/edit/{id}")
    public String showEditVenueForm(@PathVariable int id, Model model) {
        venueService.getVenueById(id).ifPresent(venue -> model.addAttribute("venue", venue));
        return "editVenue";
    }

    // 5. Update venue - URL: /venues/update
    @PostMapping("/update")
    public String updateVenue(@ModelAttribute("venue") Venue venue) {
        venueService.updateVenue(venue);
        return "redirect:/venues";
    }

    // 6. Delete venue - URL: /venues/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable int id) {
        venueService.deleteVenue(id);
        return "redirect:/venues";
    }
}