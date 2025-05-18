package com.storedemo.librarysystem.DTOs.Book;

import com.storedemo.librarysystem.Entities.Author;

public record CreateBookDTO(
        String title,
        int publicationYear,
        int availableCopies,
        int totalCopies,
        Long authorId
) {

}
