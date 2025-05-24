package com.event.venuemanagement.controller;

import com.event.venuemanagement.model.Venue;
import com.event.venuemanagement.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VenueController {

    @Autowired
    private VenueService venueService;

    // Displays the list of all venues (homepage)
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("venues", venueService.getAllVenues());
        return "index"; // Renders index.html
    }

    // Displays the form to add a new venue
    @GetMapping("/add-venue")
    public String showAddVenueForm(Model model) {
        model.addAttribute("venue", new Venue()); // Add an empty Venue object for the form
        return "addVenue"; // Renders addVenue.html
    }

    // Handles the submission of the new venue form
    @PostMapping("/save-venue")
    public String saveVenue(@ModelAttribute("venue") Venue venue) {
        venueService.addVenue(venue);
        return "redirect:/"; // Redirects to the homepage after saving
    }

    // Displays the form to edit an existing venue
    @GetMapping("/edit-venue/{id}")
    public String showEditVenueForm(@PathVariable int id, Model model) {
        venueService.getVenueById(id).ifPresent(venue -> model.addAttribute("venue", venue));
        return "editVenue"; // Renders editVenue.html
    }

    // Handles the submission of the edited venue form
    @PostMapping("/update-venue")
    public String updateVenue(@ModelAttribute("venue") Venue venue) {
        venueService.updateVenue(venue);
        return "redirect:/"; // Redirects to the homepage after updating
    }

    // Handles the deletion of a venue
    @GetMapping("/delete-venue/{id}")
    public String deleteVenue(@PathVariable int id) {
        venueService.deleteVenue(id);
        return "redirect:/"; // Redirects to the homepage after deleting
    }
}