package com.event.usermanagement.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String idOrUsername) {
        super("User not found: " + idOrUsername);
    }
}