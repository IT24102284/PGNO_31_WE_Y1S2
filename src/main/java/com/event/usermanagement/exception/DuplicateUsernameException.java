package com.event.usermanagement.exception;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String username) {
        super("Username already in use: " + username);
    }
}