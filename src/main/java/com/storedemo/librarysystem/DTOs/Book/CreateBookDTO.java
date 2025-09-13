package com.storedemo.librarysystem.DTOs.Book;

public record CreateBookDTO(
        String title,
        int publicationYear,
        int availableCopies,
        int totalCopies,
        Long authorId
) {

}
