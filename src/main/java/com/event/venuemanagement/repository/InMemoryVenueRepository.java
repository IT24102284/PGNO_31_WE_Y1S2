package com.event.venuemanagement.repository;

import com.event.venuemanagement.model.Venue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryVenueRepository implements com.event.venuemanagement.repository.VenueRepository {

    private final List<Venue> venues = new ArrayList<>();
    private int nextId = 1;

    @Override
    public List<Venue> findAll() {
        return venues;
    }

    @Override
    public Optional<Venue> findById(int id) {
        return venues.stream()
                .filter(venue -> venue.getId() == id)
                .findFirst();
    }

    @Override
    public void save(Venue venue) {
        venue.setId(nextId++);
        venues.add(venue);
    }

    @Override
    public void update(Venue updatedVenue) {
        findById(updatedVenue.getId()).ifPresent(venue -> {
            venue.setName(updatedVenue.getName());
            venue.setLocation(updatedVenue.getLocation());
            venue.setCapacity(updatedVenue.getCapacity());
        });
    }

    @Override
    public void deleteById(int id) {
        venues.removeIf(venue -> venue.getId() == id);
    }
}