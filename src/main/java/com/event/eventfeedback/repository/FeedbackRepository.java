package com.event.eventfeedback.repository;

import com.event.eventfeedback.model.Feedback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

// Marks this class as a Spring-managed repository component
@Repository
public class FeedbackRepository {

    // Injects the value of 'feedback.file.path' from application.properties
    @Value("${feedback.file.path}")
    private String filePath;

    /**
     * Saves a Feedback object to the text file by appending it.
     * Format: name,rating,comment
     */
    public void save(Feedback feedback) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Write feedback as a single line in CSV format
            writer.write(feedback.getName() + "," + feedback.getRating() + "," + feedback.getComment());
            writer.newLine(); // Add a new line after each feedback
        }
    }

    /**
     * Reads all feedback entries from the file and returns them as a list.
     * Assumes each line in the file is in the format: name,rating,comment
     */
    public List<Feedback> findAll() throws IOException {
        List<Feedback> feedbacks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Split the line into parts: name, rating, comment
                String[] parts = line.split(",", 3);
                // Add a new Feedback object to the list
                feedbacks.add(new Feedback(parts[0], parts[2], Integer.parseInt(parts[1])));
            }
        }
        return feedbacks;
    }
}
