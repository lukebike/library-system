package com.storedemo.librarysystem.DTOs.Loan;

import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.User.UserDTO;

import java.time.LocalDateTime;

public record LoanDTO(
        UserDTO user,
        BookDTO book,
        LocalDateTime borrowedDate,
        LocalDateTime dueDate,
        LocalDateTime returnedDate
) {
}
