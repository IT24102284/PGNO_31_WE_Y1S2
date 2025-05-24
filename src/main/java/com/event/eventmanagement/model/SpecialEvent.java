package com.event.eventmanagement.model;

public class SpecialEvent extends Event {
    private String specialGuest;

    public SpecialEvent(String name, String date, String venue, String location, double ticketPrice, String details, String specialGuest) {
        super(name, date, venue, location, ticketPrice, details);
        this.specialGuest = specialGuest;
    }

    public String getSpecialGuest() { return specialGuest; }
    public void setSpecialGuest(String specialGuest) { this.specialGuest = specialGuest; }
}
