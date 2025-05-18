package com.storedemo.librarysystem.DTOs.Author;

import com.storedemo.librarysystem.DTOs.Book.BookDTO;

import java.util.List;

public record AuthorDTO(
        Long id,
        String firstName,
        String lastName,
        String nationality,
        List<BookDTO> books) {
}
