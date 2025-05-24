package com.event.venuemanagement.repository;

import com.event.venuemanagement.model.Venue;

import java.io.*;
import java.util.*;

public class VenueRepository {
    private static final String FILE_PATH = "src/main/resources/database/venues.txt";

    public List<Venue> readAll() {
        List<Venue> venues = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    venues.add(new Venue(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3]),
                            parts[4]  // facilities
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading venues file", e);
        }
        return venues;
    }

    public Optional<Venue> findById(int id) {
        return readAll().stream()
                .filter(v -> v.getId() == id)
                .findFirst();
    }



    public void save(Venue venue) {
        try {
            File file = new File(FILE_PATH);
            // Ensure parent directories exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Append to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                String line = String.join(",",
                        String.valueOf(venue.getId()),
                        venue.getName(),
                        venue.getLocation(),
                        String.valueOf(venue.getCapacity()),
                        venue.getFacilities() != null ? venue.getFacilities() : ""
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving venue", e);
        }
    }

    public void update(Venue updatedVenue) {
        List<Venue> venues = readAll();

        boolean found = false;
        for (int i = 0; i < venues.size(); i++) {
            if (venues.get(i).getId() == updatedVenue.getId()) {
                venues.set(i, updatedVenue);
                found = true;
                break;
            }
        }

        if (!found) {
            // Optional: throw exception or add venue if not found
            throw new RuntimeException("Venue with ID " + updatedVenue.getId() + " not found for update.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Venue v : venues) {
                String line = String.join(",",
                        String.valueOf(v.getId()),
                        v.getName(),
                        v.getLocation(),
                        String.valueOf(v.getCapacity()),
                        v.getFacilities() != null ? v.getFacilities() : ""
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error updating venue", e);
        }
    }

    public void delete(int id) {
        List<Venue> venues = readAll();
        venues.removeIf(v -> v.getId() == id);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Venue v : venues) {
                writer.write(String.join(",",
                        String.valueOf(v.getId()),
                        v.getName(),
                        v.getLocation(),
                        String.valueOf(v.getCapacity()),
                        v.getFacilities()  // changed here
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting venue", e);
        }
    }
}
