package com.event.venuemanagement.repository;

import com.event.venuemanagement.model.Venue;

import java.util.List;
import java.util.Optional;

public interface VenueRepository {
    List<Venue> findAll();
    Optional<Venue> findById(int id);
    void save(Venue venue);
    void update(Venue venue);
    void deleteById(int id);
}