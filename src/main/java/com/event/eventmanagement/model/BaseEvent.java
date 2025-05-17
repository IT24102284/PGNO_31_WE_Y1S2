package com.event.eventmanagement.model;

public abstract class BaseEvent {
    private Long id;
    private String title;
    private String category;
    private String date;
    private String venue;
    private double price;

    // Constructors, Getters, Setters (Encapsulation)
    public BaseEvent() {}

    public BaseEvent(Long id, String title, String category, String date, String venue, double price) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.date = date;
        this.venue = venue;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public abstract String getEventType(); // Polymorphism
}

