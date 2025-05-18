package com.storedemo.librarysystem.DTOs;

public record SimpleAuthorDTO(
        Long id,
        String firstName,
        String lastName,
        String nationality
) {
}
