package com.event.eventfeedback.service;

import com.event.eventfeedback.model.Feedback;
import com.event.eventfeedback.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

// Marks this class as a Spring service component, enabling dependency injection
@Service
public class FeedbackService {

    // A reference to the FeedbackRepository used to perform file operations
    private final FeedbackRepository repository;

    // Constructor-based dependency injection of the repository
    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a new feedback entry by calling the repository's save method.
     * May throw IOException if the file cannot be written to.
     */
    public void addFeedback(Feedback feedback) throws IOException {
        repository.save(feedback);
    }

    /**
     * Retrieves all feedback entries from the repository (file).
     * May throw IOException if the file cannot be read.
     */
    public List<Feedback> getAllFeedbacks() throws IOException {
        return repository.findAll();
    }
}
