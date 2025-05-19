package com.storedemo.librarysystem.DTOs.User;

public record CreateUserDTO(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
