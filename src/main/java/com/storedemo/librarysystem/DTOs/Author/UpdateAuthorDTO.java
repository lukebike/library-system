package com.storedemo.librarysystem.DTOs.Author;

public record UpdateAuthorDTO(
        String firstName,
        String lastName,
        String nationality
) {
}
