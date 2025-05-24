package com.event.eventmanagement.service;

import com.event.eventmanagement.model.Event;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final String FILE_PATH = "events.txt";

    public List<Event> readEvents() throws IOException {
        List<Event> events = new ArrayList<>();
        if (!Files.exists(Paths.get(FILE_PATH))) {
            Files.createFile(Paths.get(FILE_PATH));
        }

        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 6) {
                Event e = new Event(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), parts[5]);
                events.add(e);
            }
        }

        return events;
    }

    public void saveOrUpdateEvent(Event event) throws IOException {
        List<Event> events = readEvents();
        events.removeIf(e -> e.getName().equalsIgnoreCase(event.getName()));
        events.add(event);
        writeAll(events);
    }

    public void deleteEvent(String eventName) throws IOException {
        List<Event> events = readEvents();
        events.removeIf(e -> e.getName().equalsIgnoreCase(eventName));
        writeAll(events);
    }

    public void writeAll(List<Event> events) throws IOException {
        List<String> lines = events.stream().map(e ->
                        String.join(",", e.getName(), e.getDate(), e.getVenue(), e.getLocation(),
                                String.valueOf(e.getTicketPrice()), e.getDetails()))
                .collect(Collectors.toList());

        Files.write(Paths.get(FILE_PATH), lines);
    }

    public Event findByName(String name) throws IOException {
        return readEvents().stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Merge sort by event name
    public List<Event> mergeSortByName(List<Event> events) {
        if (events.size() <= 1) return events;

        int mid = events.size() / 2;
        List<Event> left = mergeSortByName(events.subList(0, mid));
        List<Event> right = mergeSortByName(events.subList(mid, events.size()));

        return merge(left, right);
    }

    private List<Event> merge(List<Event> left, List<Event> right) {
        List<Event> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getName().compareToIgnoreCase(right.get(j).getName()) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));
        return merged;
    }
}
