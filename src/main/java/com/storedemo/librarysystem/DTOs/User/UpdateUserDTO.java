package com.storedemo.librarysystem.DTOs.User;

public record UpdateUserDTO(String firstName,
                            String lastName,
                            String email,
                            String currentPassword,
                            String newPassword) {

}
