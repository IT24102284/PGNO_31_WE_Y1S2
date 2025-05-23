package com.event.eventfeedback.model;

// The Feedback class represents a single feedback entry from a user
public class Feedback {
    // The name of the user providing the feedback
    private String name;

    // The comment or message from the user
    private String comment;

    // The rating given by the user (e.g., from 1 to 5 stars)
    private int rating;

    // Default constructor required for frameworks like Spring or when creating an empty object
    public Feedback() {}

    // Parameterized constructor to create a Feedback object with initial values
    public Feedback(String name, String comment, int rating) {
        this.name = name;
        this.comment = comment;
        this.rating = rating;
    }

    // Getter method for the name field
    public String getName() { return name; }

    // Setter method for the name field
    public void setName(String name) { this.name = name; }

    // Getter method for the comment field
    public String getComment() { return comment; }

    // Setter method for the comment field
    public void setComment(String comment) { this.comment = comment; }

    // Getter method for the rating field
    public int getRating() { return rating; }

    // Setter method for the rating field
    public void setRating(int rating) { this.rating = rating; }
}
