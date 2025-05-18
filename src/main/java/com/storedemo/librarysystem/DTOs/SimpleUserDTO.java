package com.storedemo.librarysystem.DTOs;

public record SimpleUserDTO(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
