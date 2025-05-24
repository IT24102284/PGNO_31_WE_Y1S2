package com.event.eventfeedback.repository;

import com.event.eventfeedback.model.Feedback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

// Marks this class as a Spring-managed repository component for dependency injection
@Repository
public class FeedbackRepository {

    // Injects the value of 'feedback.file.path' from the application.properties file
    @Value("${feedback.file.path}")
    private String filePath;

    /**
     * Saves a single Feedback object to the file.
     * Appends the feedback to the file in CSV format: name,rating,comment
     *
     * @param feedback the Feedback object to save
     * @throws IOException if an I/O error occurs
     */
    public void save(Feedback feedback) throws IOException {
        // Open the file in append mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Write feedback data in CSV format: name,rating,comment
            writer.write(feedback.getName() + "," + feedback.getRating() + "," + feedback.getComment());
            writer.newLine(); // Add a newline to separate each feedback entry
        }
    }

    /**
     * Reads all feedback entries from the file and returns them as a list.
     *
     * @return a list of Feedback objects
     * @throws IOException if an I/O error occurs
     */
    public List<Feedback> findAll() throws IOException {
        List<Feedback> feedbacks = new ArrayList<>();
        // Open the file for reading
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Split the line into parts: name, rating, comment
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    // Create a new Feedback object using the parsed data
                    feedbacks.add(new Feedback(parts[0], parts[2], Integer.parseInt(parts[1])));
                }
            }
        }
        return feedbacks;
    }
}
