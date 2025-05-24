package com.event.venuemanagement.service;

import com.event.venuemanagement.model.Venue;
import com.event.venuemanagement.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService() {
        this.venueRepository = new VenueRepository();
    }

    public List<Venue> getAllVenues() {
        List<Venue> venues = venueRepository.readAll();
        System.out.println("Loaded venues count: " + venues.size());
        venues.forEach(v -> System.out.println("Venue: " + v.getId() + ", " + v.getName()));
        return venues;
    }

    public void addVenue(Venue venue) {
        // Generate unique ID: max existing ID + 1
        int newId = venueRepository.readAll().stream()
                .mapToInt(Venue::getId)
                .max()
                .orElse(0) + 1;
        System.out.println("Assigning new venue ID: " + newId);

        venue.setId(newId);
        venueRepository.save(venue);
    }

    public Optional<Venue> getVenueById(int id) {
        return venueRepository.findById(id);
    }

    public void updateVenue(Venue venue) {
        venueRepository.update(venue);
    }

    public void deleteVenue(int id) {
        venueRepository.delete(id);
    }
}