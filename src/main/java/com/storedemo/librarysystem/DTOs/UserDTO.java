package com.storedemo.librarysystem.DTOs;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        LocalDateTime registrationDate
) {
}
