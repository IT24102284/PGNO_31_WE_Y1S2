package com.event.venuemanagement.service;

import com.event.venuemanagement.model.Venue;
import com.event.venuemanagement.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public void addVenue(Venue venue) {
        venueRepository.save(venue);
    }

    public Optional<Venue> getVenueById(int id) {
        return venueRepository.findById(id);
    }

    public void updateVenue(Venue venue) {
        venueRepository.update(venue);
    }

    public void deleteVenue(int id) {
        venueRepository.deleteById(id);
    }
}