package com.storedemo.librarysystem.DTOs.Loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storedemo.librarysystem.DTOs.Book.BookDTO;
import com.storedemo.librarysystem.DTOs.User.UserDTO;

import java.time.LocalDateTime;

public record LoanDTO(
        Long loanId,
        UserDTO user,
        BookDTO book,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime borrowedDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dueDate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime returnedDate
) {
}
