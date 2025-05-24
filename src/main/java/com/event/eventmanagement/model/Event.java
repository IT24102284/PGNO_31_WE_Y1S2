package com.event.eventmanagement.model;

public class Event {
    private String name;
    private String date;
    private String venue;
    private String location;
    private double ticketPrice;
    private String details;

    public Event() {}

    public Event(String name, String date, String venue, String location, double ticketPrice, String details) {
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.location = location;
        this.ticketPrice = ticketPrice;
        this.details = details;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
