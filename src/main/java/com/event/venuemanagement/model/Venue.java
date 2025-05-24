package com.event.venuemanagement.model;

public class Venue {
    private int id;
    private String name;
    private String location;
    private int capacity;
    private String facilities;  // New field for facilities description

    public Venue() {}

    public Venue(int id, String name, String location, int capacity, String facilities) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.facilities = facilities;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
}