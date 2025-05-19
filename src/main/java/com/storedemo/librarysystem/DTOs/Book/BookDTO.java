package com.storedemo.librarysystem.DTOs.Book;

import com.storedemo.librarysystem.DTOs.Author.AuthorDTO;


public record BookDTO(
        Long id,
        String title,
        int publicationYear,
        int availableCopies,
        int totalCopies,
        AuthorDTO author
) {

}
