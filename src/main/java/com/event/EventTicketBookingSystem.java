package com.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ticketbooking")
public class EventTicketBookingSystem {
    public static void main(String[] args) {
        SpringApplication.run(com.event.EventTicketBookingSystem.class, args);
    }
}
