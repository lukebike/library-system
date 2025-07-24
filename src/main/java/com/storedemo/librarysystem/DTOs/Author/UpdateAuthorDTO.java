package com.storedemo.librarysystem.DTOs.Author;

public record UpdateAuthorDTO(
        Long id,
        String firstName,
        String lastName,
        String nationality
) {
}
