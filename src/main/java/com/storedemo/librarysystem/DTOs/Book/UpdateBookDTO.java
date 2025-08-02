package com.storedemo.librarysystem.DTOs.Book;

public record UpdateBookDTO(String title,  int publicationYear,
                            int availableCopies, long authorId) {

}
