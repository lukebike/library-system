package com.storedemo.librarysystem.ExceptionHandler;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String message) {
        super(message);
    }
}
