package com.storedemo.librarysystem.ExceptionHandler;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {

        super(message);
    }
}
