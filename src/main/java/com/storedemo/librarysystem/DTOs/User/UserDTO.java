package com.storedemo.librarysystem.DTOs.User;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime registrationDate
) {
}
