package com.storedemo.librarysystem.ExceptionHandler;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
